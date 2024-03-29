/*
 * The MIT License
 *
 * Copyright (c) 2009-2021 PrimeTek
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.primefaces.showcase.view.data;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import io.quarkus.runtime.annotations.RegisterForReflection;

@Named
@RequestScoped
public class ChronolineView {

  private List<Event> events;
  private List<String> events2;

  @PostConstruct
  public void init() {
    this.events = new ArrayList<>();
    this.events.add(new Event("Ordered", "15/10/2020 10:30", "pi pi-shopping-cart", "#9C27B0", "game-controller.jpg"));
    this.events.add(new Event("Processing", "15/10/2020 14:00", "pi pi-cog", "#673AB7"));
    this.events.add(new Event("Shipped", "15/10/2020 16:15", "pi pi-shopping-cart", "#FF9800"));
    this.events.add(new Event("Delivered", "16/10/2020 10:00", "pi pi-check", "#607D8B"));

    this.events2 = new ArrayList<>();
    this.events2.add("2020");
    this.events2.add("2021");
    this.events2.add("2022");
    this.events2.add("2023");
  }

  public List<Event> getEvents() {
    return this.events;
  }

  public List<String> getEvents2() {
    return this.events2;
  }

  @RegisterForReflection
  public static class Event {
    String status;
    String date;
    String icon;
    String color;
    String image;

    public Event() {
    }

    public Event(String status, String date, String icon, String color) {
      this.status = status;
      this.date = date;
      this.icon = icon;
      this.color = color;
    }

    public Event(String status, String date, String icon, String color, String image) {
      this.status = status;
      this.date = date;
      this.icon = icon;
      this.color = color;
      this.image = image;
    }

    public String getStatus() {
      return this.status;
    }

    public void setStatus(String status) {
      this.status = status;
    }

    public String getDate() {
      return this.date;
    }

    public void setDate(String date) {
      this.date = date;
    }

    public String getIcon() {
      return this.icon;
    }

    public void setIcon(String icon) {
      this.icon = icon;
    }

    public String getColor() {
      return this.color;
    }

    public void setColor(String color) {
      this.color = color;
    }

    public String getImage() {
      return this.image;
    }

    public void setImage(String image) {
      this.image = image;
    }
  }

}
