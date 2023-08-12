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
package org.primefaces.showcase.view.csv;

import java.time.LocalDate;
import java.util.Date;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class ValidationView {

  private String text;
  private String description;
  private Integer integer;
  private Double doubleNumber;
  private Double money;
  private String regexText;
  private Date date;
  private Date date2;
  private Date date3;
  private LocalDate localDate;
  private LocalDate localDate2;
  private LocalDate localDate3;

  public String getText() {
    return this.text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getInteger() {
    return this.integer;
  }

  public void setInteger(Integer integer) {
    this.integer = integer;
  }

  public Double getDoubleNumber() {
    return this.doubleNumber;
  }

  public void setDoubleNumber(Double doubleNumber) {
    this.doubleNumber = doubleNumber;
  }

  public Double getMoney() {
    return this.money;
  }

  public void setMoney(Double money) {
    this.money = money;
  }

  public String getRegexText() {
    return this.regexText;
  }

  public void setRegexText(String regexText) {
    this.regexText = regexText;
  }

  public Date getDate() {
    return this.date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Date getDate2() {
    return this.date2;
  }

  public void setDate2(Date date) {
    this.date2 = date;
  }

  public Date getDate3() {
    return this.date3;
  }

  public void setDate3(Date date) {
    this.date3 = date;
  }

  public LocalDate getLocalDate() {
    return this.localDate;
  }

  public void setLocalDate(LocalDate localDate) {
    this.localDate = localDate;
  }

  public LocalDate getLocalDate2() {
    return this.localDate2;
  }

  public void setLocalDate2(LocalDate localDate) {
    this.localDate2 = localDate;
  }

  public LocalDate getLocalDate3() {
    return this.localDate3;
  }

  public void setLocalDate3(LocalDate localDate) {
    this.localDate3 = localDate;
  }
}
