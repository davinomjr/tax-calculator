package com.davinomjr.taxcalculator.core.entities;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Orders")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Order implements Serializable {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @JsonIgnore
        @OneToMany(
                cascade = CascadeType.ALL,
                orphanRemoval = true
        )
        @JoinColumn(name = "order_id")
        private List<OrderItem> items;

        @JsonIgnore
        private Date date;

        public Order(){}
        public Order(Date date, List<OrderItem> items) {
                this.date = date;
                this.items = items;
        }

        public List<OrderItem> getItems() { return this.items; }

        public Date getDate() {
                return date;
        }

        public void setDate(Date date) {
                this.date = date;
        }
}
