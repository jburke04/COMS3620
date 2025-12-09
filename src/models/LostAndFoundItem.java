package models;

public class LostAndFoundItem {

    private final int id;
    private String description;
    private String locationFound;
    private Integer guestId;
    private LostItemStatus status;
    private String dateFound;

    public LostAndFoundItem(int id,
                            String description,
                            String locationFound,
                            Integer guestId,
                            LostItemStatus status,
                            String dateFound) {
        this.id = id;
        this.description = description;
        this.locationFound = locationFound;
        this.guestId = guestId;
        this.status = status;
        this.dateFound = dateFound;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getLocationFound() {
        return locationFound;
    }

    public Integer getGuestId() {
        return guestId;
    }

    public LostItemStatus getStatus() {
        return status;
    }

    public String getDateFound() {
        return dateFound;
    }

    public void setStatus(LostItemStatus status) {
        this.status = status;
    }

    public void setGuestId(Integer guestId) {
        this.guestId = guestId;
    }

    @Override
    public String toString() {
        String ownerPart = (guestId == null)
                ? "owner=UNKNOWN"
                : "guestId=" + guestId;
        return String.format(
                "[ID=%d] %s | found at: %s | %s | status=%s | date=%s",
                id,
                description,
                locationFound,
                ownerPart,
                status,
                dateFound
        );
    }
}
