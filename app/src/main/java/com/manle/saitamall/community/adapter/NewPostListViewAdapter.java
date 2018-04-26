package com.manle.saitamall.community.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.manle.saitamall.R;
import com.manle.saitamall.bean.Artical;
import com.manle.saitamall.bean.ArticalComment;
import com.manle.saitamall.bean.User;
import com.manle.saitamall.community.bean.ArticalVO;
import com.manle.saitamall.utils.CacheUtils;
import com.manle.saitamall.utils.Constants;
import com.bumptech.glide.Glide;
import com.manle.saitamall.utils.GlideCircleTransform;
import com.opendanmaku.DanmakuItem;
import com.opendanmaku.DanmakuView;
import com.opendanmaku.IDanmakuItem;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.MediaType;

import static com.opendanmaku.DanmakuView.TAG;

// 新帖
public class NewPostListViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<ArticalVO> result;
    private List<ArticalComment> comment_list;
    User user;

    public NewPostListViewAdapter(Context mContext, List<ArticalVO> result) {
        this.mContext = mContext;
        this.result = result;
    }


    @Override
    public int getCount() {
        return result.size();
    }

    @Override
    public Object getItem(int position) {
        return result.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        user = new Gson().fromJson(CacheUtils.getString(mContext, "user"), User.class);

        convertView = View.inflate(mContext, R.layout.item_listview_newpost, null);
        holder = new ViewHolder(convertView);

        final ArticalVO resultBean = result.get(position);
        comment_list = resultBean.getArticalCommentList();
        resultBean.setArticalCommentList(comment_list);
        holder.tvCommunityUsername.setText(resultBean.getUser().getUserName());
        Glide.with(mContext).load(Constants.AVATAR_IMAGE + resultBean.getUser().getFigure()).centerCrop()
                .transform(new GlideCircleTransform(mContext)).into(holder.ibNewPostAvatar);
        Glide.with(mContext)
                .load(Constants.ARTICAL_IMAGE + resultBean.getFigure())
                .into(holder.ivCommunityFigure);
        changeImageSize(holder.tvCommunityLikes, holder.tvCommunityComments, holder.etSendComments);
        holder.tvCommunitySaying.setText(resultBean.getTitle());
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        holder.tvCommunityAddtime.setText(fm.format(resultBean.getCreatDate()));
        holder.tvCommunityLikes.setChecked(resultBean.isLike);
        holder.tvCommunityLikes.setText(resultBean.getFavorite() + "");
        holder.tvCommunityLikes.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
               OkHttpUtils.get().url(Constants.LIKE_ADD).addParams("id",resultBean.getId()+"").build().execute(new StringCallback() {
                   @Override
                   public void onError(Call call, Exception e, int id) {
                       ToastUtils.showShortToast(mContext,"点赞失败");
                       holder.tvCommunityLikes.setChecked(false);
                   }

                   @Override
                   public void onResponse(String response, int id) {
                       resultBean.isLike = true;
                       resultBean.setFavorite(resultBean.getFavorite() + 1);
                       holder.tvCommunityLikes.setText(resultBean.getFavorite() + "");
                   }
               });
            }else {
                OkHttpUtils.get().url(Constants.LIKE_REMOVE).addParams("id",resultBean.getId()+"").build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.showShortToast(mContext,"取消失败");
                        holder.tvCommunityLikes.setChecked(true);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        resultBean.isLike = false;
                        resultBean.setFavorite(resultBean.getFavorite() - 1);
                        holder.tvCommunityLikes.setText(resultBean.getFavorite() + "");
                    }
                });
            }
        });
        if (resultBean.getArticalCommentList() != null) {
            holder.tvCommunityComments.setText(resultBean.getArticalCommentList().size() + "");
        } else {
            holder.tvCommunityComments.setText(0 + "");
        }


        holder.tvCommunityComments.setOnClickListener(v -> {
            holder.etSendComments.setVisibility(View.VISIBLE);
            holder.tvCommunityLikes.setVisibility(View.GONE);
            holder.tvCommunityComments.setVisibility(View.GONE);
        });
        holder.etSendComments.setOnEditorActionListener((v, actionId, event) -> {
            if (user != null && actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                holder.etSendComments.setVisibility(View.GONE);
                holder.tvCommunityLikes.setVisibility(View.VISIBLE);
                holder.tvCommunityComments.setVisibility(View.VISIBLE);
                if (resultBean.getArticalCommentList()==null){
                    resultBean.setArticalCommentList(new ArrayList<>());
                }
                ArticalComment comment = new ArticalComment(v.getText().toString(), resultBean.getId(), user.getId());
                changeOnServer(comment,resultBean,holder);

            } else if (user == null) {
                ToastUtils.showShortToast(mContext, "请登录后评论");
            }
            return false;
        });


        //设置弹幕

        if (comment_list != null && comment_list.size() > 0) {
            holder.danmakuView.setVisibility(View.VISIBLE);

            List<IDanmakuItem> list = new ArrayList<>();
            for (int i = 0; i < comment_list.size(); i++) {
                IDanmakuItem item = new DanmakuItem(mContext, comment_list.get(i).getContent(), holder.danmakuView.getWidth());
                list.add(item);
            }
            Collections.shuffle(comment_list);
            holder.danmakuView.addItem(list, true);
            holder.danmakuView.show();
        } else {
            holder.danmakuView.setVisibility(View.GONE);
        }

        return convertView;
    }

    private void changeOnServer(ArticalComment comment, ArticalVO resultBean, ViewHolder holder) {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        OkHttpUtils.postString().mediaType(JSON).content(new Gson().toJson(comment)).url(Constants.ARTICAL_COMMENT).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e(TAG, "onError: "+e.getMessage() );
                ToastUtils.showShortToast(mContext,"网络连接失败");
            }

            @Override
            public void onResponse(String response, int id) {
                resultBean.getArticalCommentList().add(comment);
                holder.tvCommunityComments.setText( resultBean.getArticalCommentList().size() + "");
                holder.danmakuView.addItem(new DanmakuItem(mContext, holder.etSendComments.getText().toString(), holder.danmakuView.getWidth()));
                holder.danmakuView.setVisibility(View.VISIBLE);
                holder.danmakuView.show();
            }
        });
    }



    static class ViewHolder {
        @Bind(R.id.tv_community_username)
        TextView tvCommunityUsername;
        @Bind(R.id.tv_community_addtime)
        TextView tvCommunityAddtime;
        @Bind(R.id.rl)
        RelativeLayout rl;
        @Bind(R.id.iv_community_figure)
        ImageView ivCommunityFigure;
        @Bind(R.id.danmakuView)
        DanmakuView danmakuView;
        @Bind(R.id.tv_community_saying)
        TextView tvCommunitySaying;
        @Bind(R.id.tv_community_likes)
        CheckBox tvCommunityLikes;
        @Bind(R.id.tv_community_comments)
        RadioButton tvCommunityComments;
        @Bind(R.id.ib_new_post_avatar)
        ImageButton ibNewPostAvatar;
        @Bind(R.id.et_send_comment)
        EditText etSendComments;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    private void changeImageSize(TextView tvHotLikes, TextView tvHotComments, EditText etSendComments) {
        //定义底部标签图片大小
        Drawable drawableLike = mContext.getDrawable(R.drawable.community_like_selector);
        drawableLike.setBounds(0, 0, 69, 69);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        tvHotLikes.setCompoundDrawables(drawableLike, null, null, null);//只放上面
        Drawable drawableComment = mContext.getDrawable(R.drawable.community_message_icon);
        drawableComment.setBounds(0, 0, 69, 69);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        tvHotComments.setCompoundDrawables(drawableComment, null, null, null);//只放上面
        Drawable drawableSend = mContext.getDrawable(R.drawable.send_comment_black);
        drawableSend.setBounds(0, 0, 49, 49);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        etSendComments.setCompoundDrawables(drawableSend, null, null, null);//只放上面
    }

    public void AddHeaderItem(List<ArticalVO> items) {
        result.addAll(0, items);
        notifyDataSetChanged();
    }


}
