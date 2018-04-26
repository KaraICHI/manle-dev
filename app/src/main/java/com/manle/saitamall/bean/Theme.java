package com.manle.saitamall.bean;


import org.json.JSONObject;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Theme entity.
 */
public class Theme  extends JSONObject implements Serializable {

    private static final long serialVersionUID = -6112700827392123543L;
    private Long id;

    private String themeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Theme themeDTO = (Theme) o;
        if(themeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), themeDTO.getId());
    }


}
