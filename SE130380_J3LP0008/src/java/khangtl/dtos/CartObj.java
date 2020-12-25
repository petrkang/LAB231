/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package khangtl.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Peter
 */
public class CartObj implements Serializable {

    private int userId;
    private Map<TourDTO, Integer> items;
    private ArrayList<Integer> storeId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Map<TourDTO, Integer> getItems() {
        return items;
    }

    public ArrayList<Integer> getStoreId() {
        if (storeId == null) {
            storeId = new ArrayList<>();
        }
        return storeId;
    }

    public void saveId(int id) {
        if (this.storeId == null) {
            this.storeId = new ArrayList<>();
        }
        if (!this.storeId.contains(id)) {
            this.storeId.add(id);
        }
    }

    public void removeId(int id) {
        if (this.storeId == null) {
            return;
        }
        if (this.storeId.contains(id)) {
            this.storeId.remove(Integer.valueOf(id));
            if (this.storeId.isEmpty()) {
                this.storeId = null;
            }
        }
    }

    public TourDTO getItemById(int id) {
        TourDTO dto = null;
        for (Map.Entry<TourDTO, Integer> entry : this.items.entrySet()) {
            if (entry.getKey().getId() == id) {
                dto = entry.getKey();
                break;
            }
        }
        return dto;
    }

    public void addItemToCart(TourDTO dto, int amount) {
        if (this.items == null) {
            this.items = new HashMap<>();
        }
        int quantity = amount;
        if (this.items.containsKey(dto)) {
            quantity = this.items.get(dto) + amount;
        }
        this.items.put(dto, quantity);
    }

    public void updateItemCart(TourDTO dto, int amount) {
        if (this.items == null) {
            this.items = new HashMap<>();
        }
        if (this.items.containsKey(dto)) {
            this.items.put(dto, amount);
        }
    }

    public void removeItemFromCart(TourDTO dto) {
        if (this.items == null) {
            return;
        }
        if (this.items.containsKey(dto)) {
            this.items.remove(dto);
            if (this.items.isEmpty()) {
                this.items = null;
            }
        }
    }

}
