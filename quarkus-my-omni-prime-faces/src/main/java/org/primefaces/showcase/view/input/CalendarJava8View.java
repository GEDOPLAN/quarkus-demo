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
package org.primefaces.showcase.view.input;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.validation.constraints.Future;

import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

@Named
@ViewScoped
public class CalendarJava8View implements Serializable {

  private LocalDate date;
  private LocalDate date1;
  @Future
  private LocalDate date2;
  private LocalDate date3;
  private LocalDate date4;
  private LocalDate date5;
  private LocalDate date6;
  private LocalDate date7;
  private LocalDate date8;
  private LocalDate date9;
  private LocalDate dateDe;
  private LocalDate date10;
  private LocalDate date11;
  private LocalDate date12;
  private LocalDate date13;
  private LocalDate date14;
  private LocalTime time1;
  private LocalTime time2;
  private LocalTime time3;
  private LocalTime time4;
  private LocalTime time5;
  private LocalTime time6;
  private LocalTime time7;
  private LocalTime time8;
  private LocalDateTime dateTime1;
  @Future
  private LocalDateTime dateTime2;
  private LocalDateTime dateTime3;
  private LocalDateTime dateTime4;
  private LocalDateTime dateTime5;
  private LocalDateTime dateTime6;
  private LocalDateTime dateTime7;
  private LocalDateTime dateTimeDe;
  private List<LocalDate> multi;
  private List<LocalDate> range;
  private List<LocalDate> invalidDates;
  private List<Integer> invalidDays;
  private LocalDate minDate;
  private LocalDate maxDate;
  private LocalTime minTime;
  private LocalTime maxTime;
  private LocalDateTime minDateTime;
  private LocalDateTime maxDateTime;
  private ZonedDateTime zonedDateTime1;
  private YearMonth yearMonth;

  @PostConstruct
  public void init() {
    this.invalidDates = new ArrayList<>();
    this.invalidDates.add(LocalDate.now());
    for (int i = 0; i < 5; i++) {
      this.invalidDates.add(this.invalidDates.get(i).plusDays(1));
    }

    this.invalidDays = new ArrayList<>();
    this.invalidDays.add(0); /* the first day of week is disabled */
    this.invalidDays.add(3);

    this.minDate = LocalDate.now().minusYears(1);
    this.maxDate = LocalDate.now().plusYears(1);

    this.minTime = LocalTime.of(9, 0);
    this.maxTime = LocalTime.of(17, 0);

    this.minDateTime = LocalDateTime.now().minusWeeks(1);
    this.maxDateTime = LocalDateTime.now().plusWeeks(1);

    this.dateDe = LocalDate.of(2019, 7, 27);
    this.dateTimeDe = LocalDateTime.of(2019, 7, 27, 12, 59);
    this.dateTime4 = LocalDateTime.now();

    this.time4 = LocalTime.of(10, 30);
  }

  public void onDateSelect(SelectEvent<LocalDate> event) {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", event.getObject().format(formatter)));
  }

  public void onDateTimeSelect(SelectEvent<LocalDateTime> event) {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", event.getObject().format(formatter)));
  }

  public void click() {
    PrimeFaces.current().ajax().update("form:display");
    PrimeFaces.current().executeScript("PF('dlg').show()");
  }

  public LocalDate getDate() {
    return this.date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public LocalDate getDate1() {
    return this.date1;
  }

  public void setDate1(LocalDate date1) {
    this.date1 = date1;
  }

  public LocalDate getDate2() {
    return this.date2;
  }

  public void setDate2(LocalDate date2) {
    this.date2 = date2;
  }

  public LocalDate getDate3() {
    return this.date3;
  }

  public void setDate3(LocalDate date3) {
    this.date3 = date3;
  }

  public LocalDate getDate4() {
    return this.date4;
  }

  public void setDate4(LocalDate date4) {
    this.date4 = date4;
  }

  public LocalDate getDate5() {
    return this.date5;
  }

  public void setDate5(LocalDate date5) {
    this.date5 = date5;
  }

  public LocalDate getDate6() {
    return this.date6;
  }

  public void setDate6(LocalDate date6) {
    this.date6 = date6;
  }

  public LocalDate getDate7() {
    return this.date7;
  }

  public void setDate7(LocalDate date7) {
    this.date7 = date7;
  }

  public LocalDate getDate8() {
    return this.date8;
  }

  public void setDate8(LocalDate date8) {
    this.date8 = date8;
  }

  public LocalDate getDate9() {
    return this.date9;
  }

  public void setDate9(LocalDate date9) {
    this.date9 = date9;
  }

  public LocalDate getDateDe() {
    return this.dateDe;
  }

  public void setDateDe(LocalDate dateDe) {
    this.dateDe = dateDe;
  }

  public LocalDate getDate10() {
    return this.date10;
  }

  public void setDate10(LocalDate date10) {
    this.date10 = date10;
  }

  public LocalDate getDate11() {
    return this.date11;
  }

  public void setDate11(LocalDate date11) {
    this.date11 = date11;
  }

  public LocalDate getDate12() {
    return this.date12;
  }

  public void setDate12(LocalDate date12) {
    this.date12 = date12;
  }

  public LocalDateTime getDateTime1() {
    return this.dateTime1;
  }

  public void setDateTime1(LocalDateTime dateTime1) {
    this.dateTime1 = dateTime1;
  }

  public LocalTime getTime1() {
    return this.time1;
  }

  public void setTime1(LocalTime time1) {
    this.time1 = time1;
  }

  public List<LocalDate> getMulti() {
    return this.multi;
  }

  public void setMulti(List<LocalDate> multi) {
    this.multi = multi;
  }

  public List<LocalDate> getRange() {
    return this.range;
  }

  public void setRange(List<LocalDate> range) {
    this.range = range;
  }

  public List<LocalDate> getInvalidDates() {
    return this.invalidDates;
  }

  public void setInvalidDates(List<LocalDate> invalidDates) {
    this.invalidDates = invalidDates;
  }

  public List<Integer> getInvalidDays() {
    return this.invalidDays;
  }

  public void setInvalidDays(List<Integer> invalidDays) {
    this.invalidDays = invalidDays;
  }

  public LocalDate getMinDate() {
    return this.minDate;
  }

  public void setMinDate(LocalDate minDate) {
    this.minDate = minDate;
  }

  public LocalDate getMaxDate() {
    return this.maxDate;
  }

  public void setMaxDate(LocalDate maxDate) {
    this.maxDate = maxDate;
  }

  public LocalDateTime getDateTimeDe() {
    return this.dateTimeDe;
  }

  public void setDateTimeDe(LocalDateTime dateTimeDe) {
    this.dateTimeDe = dateTimeDe;
  }

  public LocalTime getTime2() {
    return this.time2;
  }

  public void setTime2(LocalTime time2) {
    this.time2 = time2;
  }

  public LocalTime getTime3() {
    return this.time3;
  }

  public void setTime3(LocalTime time3) {
    this.time3 = time3;
  }

  public LocalDate getDate13() {
    return this.date13;
  }

  public void setDate13(LocalDate date13) {
    this.date13 = date13;
  }

  public LocalDate getDate14() {
    return this.date14;
  }

  public void setDate14(LocalDate date14) {
    this.date14 = date14;
  }

  public ZonedDateTime getZonedDateTime1() {
    return this.zonedDateTime1;
  }

  public void setZonedDateTime1(ZonedDateTime zonedDateTime1) {
    this.zonedDateTime1 = zonedDateTime1;
  }

  public LocalDateTime getDateTime2() {
    return this.dateTime2;
  }

  public void setDateTime2(LocalDateTime dateTime2) {
    this.dateTime2 = dateTime2;
  }

  public YearMonth getYearMonth() {
    return this.yearMonth;
  }

  public void setYearMonth(YearMonth yearMonth) {
    this.yearMonth = yearMonth;
  }

  public LocalTime getMinTime() {
    return this.minTime;
  }

  public void setMinTime(LocalTime minTime) {
    this.minTime = minTime;
  }

  public LocalTime getMaxTime() {
    return this.maxTime;
  }

  public void setMaxTime(LocalTime maxTime) {
    this.maxTime = maxTime;
  }

  public LocalDateTime getMinDateTime() {
    return this.minDateTime;
  }

  public void setMinDateTime(LocalDateTime minDateTime) {
    this.minDateTime = minDateTime;
  }

  public LocalDateTime getMaxDateTime() {
    return this.maxDateTime;
  }

  public void setMaxDateTime(LocalDateTime maxDateTime) {
    this.maxDateTime = maxDateTime;
  }

  public LocalDateTime getDateTime3() {
    return this.dateTime3;
  }

  public void setDateTime3(LocalDateTime dateTime3) {
    this.dateTime3 = dateTime3;
  }

  public LocalTime getTime4() {
    return this.time4;
  }

  public void setTime4(LocalTime time4) {
    this.time4 = time4;
  }

  public LocalTime getTime5() {
    return this.time5;
  }

  public void setTime5(LocalTime time5) {
    this.time5 = time5;
  }

  public LocalTime getTime6() {
    return this.time6;
  }

  public void setTime6(LocalTime time6) {
    this.time6 = time6;
  }

  public LocalDateTime getDateTime4() {
    return this.dateTime4;
  }

  public void setDateTime4(LocalDateTime dateTime4) {
    this.dateTime4 = dateTime4;
  }

  public LocalDateTime getDateTime5() {
    return this.dateTime5;
  }

  public void setDateTime5(LocalDateTime dateTime5) {
    this.dateTime5 = dateTime5;
  }

  public LocalDateTime getDateTime6() {
    return this.dateTime6;
  }

  public void setDateTime6(LocalDateTime dateTime6) {
    this.dateTime6 = dateTime6;
  }

  public LocalTime getTime7() {
    return this.time7;
  }

  public void setTime7(LocalTime time7) {
    this.time7 = time7;
  }

  public LocalDateTime getDateTime7() {
    return this.dateTime7;
  }

  public void setDateTime7(LocalDateTime dateTime7) {
    this.dateTime7 = dateTime7;
  }

  public LocalTime getTime8() {
    return this.time8;
  }

  public void setTime8(LocalTime time8) {
    this.time8 = time8;
  }
}
