package com.eiranling._enum;

/**
 * Enum of pre-defined badge types
 */
public enum BadgeType {
    BADGE_DEFAULT("Badge", "badge-default"),
    BADGE_SUCCESS("Success", "badge-success"),
    BADGE_FAILURE("Failure", "badge-failure"),
    BADGE_PENDING("Pending", "badge-pending"),
    BADGE_INFO("Info", "badge-info");

    private String badgeText;
    private String styleClass;

    BadgeType(String badgeText, String styleClass) {
        this.badgeText = badgeText;
        this.styleClass = styleClass;
    }

    /**
     * Gets the pre-defined text for the badge type
     * @return pre-defined text for the badge
     */
    public String getBadgeText() {
        return badgeText;
    }

    /**
     * Gets the pre-defined style class of the BadgeType
     * @return pre-defined badge style for the badge
     */
    public String getStyleClass() {
        return styleClass;
    }
}
