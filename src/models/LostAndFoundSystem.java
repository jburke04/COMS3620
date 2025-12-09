package models;

import helpers.Parser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class LostAndFoundSystem implements SubSystem {

    private final List<LostAndFoundItem> items = new ArrayList<>();
    private final String path = "src/assets/LostAndFoundItems.json";
    private int nextId = 1;

    @Override
    public void load() {
        Parser.parseLostAndFoundItems(this.path, this.items);

        int max = 0;
        for (LostAndFoundItem i : items) {
            if (i.getId() > max) {
                max = i.getId();
            }
        }
        nextId = max + 1;
    }

    @Override
    public void save() {
        Parser.saveLostAndFoundItems(this.path, this.items);
    }

    public LostAndFoundItem createItem(String description,
                                       String locationFound,
                                       Guest possibleOwner) {
        int id = nextId++;
        Integer guestId = (possibleOwner != null) ? possibleOwner.getGuestId() : null;
        LostItemStatus status = (possibleOwner != null)
                ? LostItemStatus.OWNER_KNOWN
                : LostItemStatus.UNCLAIMED;

        String dateFound = Parser.isoFromCalendar(Calendar.getInstance());

        LostAndFoundItem item = new LostAndFoundItem(
                id,
                description,
                locationFound,
                guestId,
                status,
                dateFound
        );
        items.add(item);
        save();
        return item;
    }

    public List<LostAndFoundItem> getItems() {
        return items;
    }

    public LostAndFoundItem findById(int id) {
        for (LostAndFoundItem i : items) {
            if (i.getId() == id) {
                return i;
            }
        }
        return null;
    }

    public List<LostAndFoundItem> searchByText(String text) {
        String q = text.toLowerCase();
        List<LostAndFoundItem> result = new ArrayList<>();
        for (LostAndFoundItem i : items) {
            String desc = (i.getDescription() == null) ? "" : i.getDescription().toLowerCase();
            String loc  = (i.getLocationFound() == null) ? "" : i.getLocationFound().toLowerCase();
            if (desc.contains(q) || loc.contains(q)) {
                result.add(i);
            }
        }
        return result;
    }

    public void markClaimed(LostAndFoundItem item) {
        if (item == null) return;
        item.setStatus(LostItemStatus.CLAIMED);
        save();
    }
}
