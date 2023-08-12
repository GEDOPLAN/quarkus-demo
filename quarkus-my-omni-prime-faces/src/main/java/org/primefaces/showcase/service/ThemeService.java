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
package org.primefaces.showcase.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import org.primefaces.showcase.domain.Theme;

@Named
@ApplicationScoped
public class ThemeService {

  private List<Theme> themes;

  @PostConstruct
  public void init() {
      this.themes = new ArrayList<>();
      this.themes.add(new Theme(0, "Nova-Light", "nova-light"));
      this.themes.add(new Theme(1, "Nova-Dark", "nova-dark"));
      this.themes.add(new Theme(2, "Nova-Colored", "nova-colored"));
      this.themes.add(new Theme(3, "Luna-Blue", "luna-blue"));
      this.themes.add(new Theme(4, "Luna-Amber", "luna-amber"));
      this.themes.add(new Theme(5, "Luna-Green", "luna-green"));
      this.themes.add(new Theme(6, "Luna-Pink", "luna-pink"));
      this.themes.add(new Theme(7, "Omega", "omega"));
  }

  public List<Theme> getThemes() {
    return this.themes;
  }
}
