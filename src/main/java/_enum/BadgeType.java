package _enum;

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

    public String getBadgeText() {
        return badgeText;
    }

    public String getStyleClass() {
        return styleClass;
    }
}
