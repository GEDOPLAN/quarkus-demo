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
package org.primefaces.showcase.view.data.treetable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.primefaces.model.TreeNode;
import org.primefaces.showcase.service.DocumentService;

@Named("ttColumnsView")
@ViewScoped
public class ColumnsView implements Serializable {

  private final static List<String> VALID_COLUMN_KEYS = Arrays.asList("name", "size", "type");
  @Inject
  DocumentService service;
  private String columnTemplate = "name size type";
  private List<ColumnModel> columns;
  private TreeNode root;

  @PostConstruct
  public void init() {
    this.root = this.service.createDocuments();

    createDynamicColumns();
  }

  public TreeNode getRoot() {
    return this.root;
  }

  public void setService(DocumentService service) {
    this.service = service;
  }

  public void createDynamicColumns() {
    String[] columnKeys = this.columnTemplate.split(" ");
    this.columns = new ArrayList<ColumnModel>();

    for (String columnKey : columnKeys) {
      String key = columnKey.trim();

      if (VALID_COLUMN_KEYS.contains(key)) {
        this.columns.add(new ColumnModel(columnKey, columnKey));
      }
    }
  }

  public String getColumnTemplate() {
    return this.columnTemplate;
  }

  public void setColumnTemplate(String columnTemplate) {
    this.columnTemplate = columnTemplate;
  }

  public List<ColumnModel> getColumns() {
    return this.columns;
  }

  @RegisterForReflection
  static public class ColumnModel implements Serializable {

    private String header;
    private String property;

    public ColumnModel(String header, String property) {
      this.header = header;
      this.property = property;
    }

    public String getHeader() {
      return this.header;
    }

    public String getProperty() {
      return this.property;
    }
  }
}
