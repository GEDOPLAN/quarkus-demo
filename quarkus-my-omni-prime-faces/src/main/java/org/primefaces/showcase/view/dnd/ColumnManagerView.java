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
package org.primefaces.showcase.view.dnd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.showcase.domain.Product;
import org.primefaces.showcase.service.ProductService;

@Named
@ViewScoped
public class ColumnManagerView implements Serializable {

  private static final List<String> VALID_COLUMN_KEYS = Arrays.asList("id", "code", "name", "description",
    "price", "category", "quantity");
  @Inject
  ProductService service;
  private List<ColumnModel> columns = new ArrayList<>();
  private List<Product> products;
  private TreeNode<Product> availableColumns;

  @PostConstruct
  public void init() {
    this.products = this.service.getProducts(9);
    createAvailableColumns();
    createDynamicColumns();
  }

  private void createAvailableColumns() {
    this.availableColumns = new DefaultTreeNode("Root", null);
    TreeNode root = new DefaultTreeNode("Columns", this.availableColumns);
    root.setExpanded(true);
    TreeNode id = new DefaultTreeNode("column", new ColumnModel("Id", "id"), root);
    TreeNode code = new DefaultTreeNode("column", new ColumnModel("Code", "code"), root);
    TreeNode name = new DefaultTreeNode("column", new ColumnModel("Name", "name"), root);
    TreeNode description = new DefaultTreeNode("column", new ColumnModel("Description", "description"), root);
    TreeNode price = new DefaultTreeNode("column", new ColumnModel("Price", "price"), root);
    TreeNode category = new DefaultTreeNode("column", new ColumnModel("Category", "category"), root);
    TreeNode quantity = new DefaultTreeNode("column", new ColumnModel("Quantity", "quantity"), root);
  }

  public void createDynamicColumns() {
    String[] columnKeys = new String[] { "code", "name", "quantity" };
    this.columns.clear();

    for (String columnKey : columnKeys) {
      String key = columnKey.trim();

      if (VALID_COLUMN_KEYS.contains(key)) {
        this.columns.add(new ColumnModel(columnKey.toUpperCase(), columnKey));
      }
    }
  }

  public void treeToTable() {
    Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    String property = params.get("property");
    String droppedColumnId = params.get("droppedColumnId");
    String dropPos = params.get("dropPos");

    String[] droppedColumnTokens = droppedColumnId.split(":");
    int draggedColumnIndex = Integer.parseInt(droppedColumnTokens[droppedColumnTokens.length - 1]);
    int dropColumnIndex = draggedColumnIndex + Integer.parseInt(dropPos);

    //add to columns
    this.columns.add(dropColumnIndex, new ColumnModel(property.toUpperCase(), property));

    //remove from nodes
    TreeNode<?> root = this.availableColumns.getChildren().get(0);
    for (TreeNode node : root.getChildren()) {
      ColumnModel model = (ColumnModel) node.getData();
      if (model.getProperty().equals(property)) {
        root.getChildren().remove(node);
        break;
      }
    }
  }

  public void tableToTree() {
    Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    int colIndex = Integer.parseInt(params.get("colIndex"));

    //remove from table
    ColumnModel model = this.columns.remove(colIndex);

    //add to nodes
    TreeNode property = new DefaultTreeNode("column", model, this.availableColumns.getChildren().get(0));
  }

  public List<Product> getProducts() {
    return this.products;
  }

  public List<ColumnModel> getColumns() {
    return this.columns;
  }

  public TreeNode getAvailableColumns() {
    return this.availableColumns;
  }

  public void setService(ProductService service) {
    this.service = service;
  }

  @RegisterForReflection
  public static class ColumnModel implements Serializable {

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
