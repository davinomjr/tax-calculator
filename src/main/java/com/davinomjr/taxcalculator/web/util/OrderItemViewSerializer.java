package com.davinomjr.taxcalculator.web.util;

import com.davinomjr.taxcalculator.web.views.OrderItemView;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;

public class OrderItemViewSerializer extends JsonSerializer<List<OrderItemView>> {

    @Override
    public void serialize(List<OrderItemView> orderItems, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartArray();
        for (OrderItemView orderItem : orderItems) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeObjectField("Item", orderItem);
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
    }
}
