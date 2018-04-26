package com.manle.saitamall.bean.enumeration;

/**
 * The OrderStatus enumeration.
 */
public enum OrderStatus {
    WAIT_TO_PAY, WAIT_TO_SEND, WAIT_TO_TAKE, DONE, WAIT_TO_COMMENT;

    @Override
    public String toString() {
        return super.toString();
    }
}
