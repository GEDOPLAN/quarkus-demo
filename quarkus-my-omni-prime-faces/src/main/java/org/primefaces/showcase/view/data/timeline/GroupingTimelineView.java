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
package org.primefaces.showcase.view.data.timeline;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.primefaces.PrimeFaces;
import org.primefaces.component.timeline.TimelineUpdater;
import org.primefaces.event.timeline.TimelineModificationEvent;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineGroup;
import org.primefaces.model.timeline.TimelineModel;
import org.primefaces.showcase.domain.Order;

@Named("groupingTimelineView")
@ViewScoped
public class GroupingTimelineView implements Serializable {

  private TimelineModel<Order, Truck> model;
  private TimelineModel<Order, Truck> model2;
  private TimelineEvent<Order> event; // current changed event
  private List<TimelineEvent<Order>> overlappedOrders; // all overlapped orders (events) to the changed order (event)
  private List<TimelineEvent<Order>> ordersToMerge; // selected orders (events) in the dialog which should be merged

  @PostConstruct
  protected void initialize() {
    // create timeline model
    this.model = newModelWithNumber(6);
    this.model2 = newModelWithNumber(30);

  }

  public TimelineModel<Order, Truck> newModelWithNumber(int n) {
    TimelineModel<Order, Truck> model = new TimelineModel<>();

    int orderNumber = 1;
    for (int j = 1; j <= n; j++) {
      model.addGroup(new TimelineGroup<Truck>("id" + j, new Truck(String.valueOf(9 + j))));
      LocalDateTime referenceDate = LocalDateTime.of(2015, Month.DECEMBER, 14, 8, 0);

      for (int i = 0; i < 6; i++) {
        LocalDateTime startDate = referenceDate.plusHours(3 * (Math.random() < 0.2 ? 1 : 0));
        LocalDateTime endDate = startDate.plusHours(2 + (int) Math.floor(Math.random() * 4));

        String imagePath = null;
        if (Math.random() < 0.25) {
          imagePath = "images/timeline/box.png";
        }

        Order order = new Order(orderNumber, imagePath);
        model.add(TimelineEvent.<Order> builder()
          .data(order)
          .startDate(startDate)
          .endDate(endDate)
          .editable(true)
          .group("id" + j)
          .build());

        orderNumber++;
        referenceDate = endDate;
      }
    }

    return model;
  }

  public TimelineModel<Order, Truck> getModel() {
    return this.model;
  }

  public TimelineModel<Order, Truck> getModel2() {
    return this.model2;
  }

  public void onChange(TimelineModificationEvent<Order> e) {
    // get changed event and update the model
    this.event = e.getTimelineEvent();
    this.model.update(this.event);

    // get overlapped events of the same group as for the changed event
    Set<TimelineEvent<Order>> overlappedEvents = this.model.getOverlappedEvents(this.event);

    if (overlappedEvents == null) {
      // nothing to merge
      return;
    }

    // list of orders which can be merged in the dialog
    this.overlappedOrders = new ArrayList<>(overlappedEvents);

    // no pre-selection
    this.ordersToMerge = null;

    // update the dialog's content and show the dialog
    PrimeFaces primefaces = PrimeFaces.current();
    primefaces.ajax().update("form:overlappedOrdersInner");
    primefaces.executeScript("PF('overlapEventsWdgt').show()");
  }

  public void onDelete(TimelineModificationEvent<Order> e) {
    // keep the model up-to-date
    this.model.delete(e.getTimelineEvent());
  }

  public void merge() {
    // merge orders and update UI if the user selected some orders to be merged
    if (this.ordersToMerge != null && !this.ordersToMerge.isEmpty()) {
      this.model.merge(this.event, this.ordersToMerge, TimelineUpdater.getCurrentInstance(":form:timeline"));
    } else {
      FacesMessage msg =
        new FacesMessage(FacesMessage.SEVERITY_INFO,
          "Nothing to merge, please choose orders to be merged", null);
      FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    this.overlappedOrders = null;
    this.ordersToMerge = null;
  }

  public int getSelectedOrder() {
    if (this.event == null) {
      return 0;
    }

    return this.event.getData().getNumber();
  }

  public List<TimelineEvent<Order>> getOverlappedOrders() {
    return this.overlappedOrders;
  }

  public List<TimelineEvent<Order>> getOrdersToMerge() {
    return this.ordersToMerge;
  }

  public void setOrdersToMerge(List<TimelineEvent<Order>> ordersToMerge) {
    this.ordersToMerge = ordersToMerge;
  }

  @RegisterForReflection
  public static class Truck implements java.io.Serializable {
    private final String code;

    public Truck(String code) {
      this.code = code;
    }

    public String getCode() {
      return this.code;
    }
  }
}  
