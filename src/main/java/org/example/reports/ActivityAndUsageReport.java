package org.example.reports;


import org.example.SmartHome.SmartHome;
import org.example.entity.device.Device;
import org.example.entity.item.Item;
import org.example.entity.residents.person.Person;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for generating activity and usage report.
 */
public class ActivityAndUsageReport {
    private final Map<Person, Map<Device, Integer>> devices = new HashMap<>();
    private final Map<Person, Map<Item, Integer>> items = new HashMap<>();
    private FileWriter activityAndUsageReport = null;

    /**
     * Instantiates a new Activity and usage report.
     */
    public ActivityAndUsageReport() {
        try {
            this.activityAndUsageReport = new FileWriter("ActivityAndUsageReport.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Counts used devices.
     *
     * @param person person that uses a device
     * @param device device that is used
     */
    public void deviceCount(Person person, Device device) {
        if (!devices.containsKey(person)) {
            devices.put(person, new HashMap<>());
        }
        if (!devices.get(person).containsKey(device)) {
            devices.get(person).put(device, 0);
        }
        devices.get(person).put(device, devices.get(person).get(device) + 1);
    }

    /**
     * Counts used items.
     *
     * @param person person that uses an item
     * @param item   item that is used
     */
    public void itemCount(Person person, Item item) {
        if (!items.containsKey(person)) {
            items.put(person, new HashMap<>());
        }
        if (!items.get(person).containsKey(item)) {
            items.get(person).put(item, 0);
        }
        items.get(person).put(item, items.get(person).get(item) + 1);
    }

    /**
     * Generates report.
     *
     * @throws IOException writing to file is unsuccessful
     */
    public void generateReport() throws IOException {
        int day = SmartHome.getInstance().getSimulation().getDay();
        activityAndUsageReport.write( day + " day \n");
        activityAndUsageReport.write("[Devices]\n");
        for (Person person : devices.keySet()) {
            for (Device device : devices.get(person).keySet()) {
                activityAndUsageReport.write("Person \"" + person.getName() + "\" has used \"" + device.getName() + "\" "
                        + devices.get(person).get(device) + " times this day.\n");
            }
        }
        activityAndUsageReport.write("[Items]\n");
        for (Person person : items.keySet()) {
            for (Item item : items.get(person).keySet()) {
                activityAndUsageReport.write("Person \"" + person.getName() + "\" has used \"" + item.getName() + "\" "
                        + items.get(person).get(item) + " times this day.\n");
            }
        }

        activityAndUsageReport.flush();

        devices.clear();
        items.clear();
    }
}
