package com.ccat.equipmentcalculator.client;

import com.ccat.equipmentcalculator.model.ItemResponse;
import com.ccat.equipmentcalculator.model.XivClientResponse;
import com.ccat.equipmentcalculator.model.entity.Item;
import com.ccat.equipmentcalculator.model.entity.enums.ClassJobCategory;
import com.ccat.equipmentcalculator.model.entity.enums.ItemSlot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class XivApiRestClient implements XivApiClient {
    @Value("${xivApi.baseUrl}")
    private String baseUrl;

    private HashMap<Integer, ItemSlot> itemSlotIdMap = new HashMap<>();

    public XivApiRestClient() {
        itemSlotIdMap.put(1, ItemSlot.PRIMARY);
        itemSlotIdMap.put(2, ItemSlot.SECONDARY);
        itemSlotIdMap.put(3, ItemSlot.HEAD);
        itemSlotIdMap.put(4, ItemSlot.BODY);
        itemSlotIdMap.put(5, ItemSlot.GLOVES);
        itemSlotIdMap.put(7, ItemSlot.LEGS);
        itemSlotIdMap.put(8, ItemSlot.FEET);
        itemSlotIdMap.put(9, ItemSlot.EARS);
        itemSlotIdMap.put(10, ItemSlot.NECK);
        itemSlotIdMap.put(11, ItemSlot.WRIST);
        itemSlotIdMap.put(12, ItemSlot.RING);
        itemSlotIdMap.put(13, ItemSlot.PRIMARY);
    }

    @Override
    public List<Item> getItemsByCategoryAndLevel(ClassJobCategory category, int level) {

        RestTemplate restTemplate = new RestTemplate();

        String requestString = String.format(
                "/search?indexes=Item&filters=ClassJobCategory.%s=1,LevelEquip=%d",
                category,
                level);
        String columnInfo = "&columns=ID,Name,LevelEquip,LevelItem,EquipSlotCategory.ID,Stats,ClassJobCategory";
        String requestUri = baseUrl + requestString + columnInfo + "&limit=250";

        XivClientResponse response = restTemplate.getForObject(requestUri, XivClientResponse.class);

        assert response != null;
        List<ItemResponse> itemData = response.getResults();

        return itemData.stream()
                .map(i -> mapItemDataToItem(i))
                .collect(Collectors.toList());
    }

    @Override
    public Item getItemById(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        String requestUri = baseUrl + "/item/" + id;
        ItemResponse itemResponse = restTemplate.getForObject(requestUri, ItemResponse.class);

        assert itemResponse != null;
        return mapItemDataToItem(itemResponse);
    }

    private Item mapItemDataToItem(ItemResponse data) {
        ItemSlot itemSlot = itemSlotIdMap.get(data.getEquipSlotCategory().get("ID"));
//        "EquipSlotCategory": {
//            "ID": 9

        HashMap<String, Integer> statMap = generateStatMap(data);
//        "Determination": {  -- Map<String, Map<String,Integer>> stats;
//        "ID": 44,
//        "NQ": 78

        Set<ClassJobCategory> jobCategorySet = generateJobCategorySet(data);
//        "ClassJobCategory": {
//            "ACN": 0,
//            "ADV": 1,
//            "ALC": 1,

        return new Item(
                data.getId(),
                data.getName(),
                data.getLevelEquip(),
                data.getLevelItem(),
                itemSlot,
                jobCategorySet,
                statMap
        );
    }

    private Set<ClassJobCategory> generateJobCategorySet(ItemResponse data) {
        Set<ClassJobCategory> jobCategories = new HashSet<>();
        for(ClassJobCategory k : ClassJobCategory.values()) {
            int i=0;

            try {
                i = Integer.parseInt(data.getClassJobCategory().get(k.toString()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            if(i == 1) {
                jobCategories.add(k);
            }
        }
        return jobCategories;
    }

    private HashMap<String,Integer> generateStatMap(ItemResponse data) {
        Set<String> keys = data.getStats().keySet();
        HashMap<String,Integer> statMap = new HashMap<>();
        for(String k : keys) {
            Map<String, Integer> statField = data.getStats().get(k);
            Integer value = statField.containsKey("HQ") ? statField.get("HQ") : statField.get("NQ");

            statMap.put(k, value);
        }
        return statMap;
    }
}