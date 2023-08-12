/*
 * The MIT License
 *
 * Copyright (c) 2009-2023 PrimeTek Informatics
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

import org.primefaces.showcase.domain.Photo;

@Named
@ApplicationScoped
public class PhotoService {

  private List<Photo> photos;

  @PostConstruct
  public void init() {
      this.photos = new ArrayList<>();

      this.photos.add(new Photo("demo/images/galleria/galleria1.jpg", "demo/images/galleria/galleria1s.jpg",
      "Description for Image 1", "Title 1"));
      this.photos.add(new Photo("demo/images/galleria/galleria2.jpg", "demo/images/galleria/galleria2s.jpg",
      "Description for Image 2", "Title 2"));
      this.photos.add(new Photo("demo/images/galleria/galleria3.jpg", "demo/images/galleria/galleria3s.jpg",
      "Description for Image 3", "Title 3"));
      this.photos.add(new Photo("demo/images/galleria/galleria4.jpg", "demo/images/galleria/galleria4s.jpg",
      "Description for Image 4", "Title 4"));
      this.photos.add(new Photo("demo/images/galleria/galleria5.jpg", "demo/images/galleria/galleria5s.jpg",
      "Description for Image 5", "Title 5"));
      this.photos.add(new Photo("demo/images/galleria/galleria6.jpg", "demo/images/galleria/galleria6s.jpg",
      "Description for Image 6", "Title 6"));
      this.photos.add(new Photo("demo/images/galleria/galleria7.jpg", "demo/images/galleria/galleria7s.jpg",
      "Description for Image 7", "Title 7"));
      this.photos.add(new Photo("demo/images/galleria/galleria8.jpg", "demo/images/galleria/galleria8s.jpg",
      "Description for Image 8", "Title 8"));
      this.photos.add(new Photo("demo/images/galleria/galleria9.jpg", "demo/images/galleria/galleria9s.jpg",
      "Description for Image 9", "Title 9"));
      this.photos.add(new Photo("demo/images/galleria/galleria10.jpg", "demo/images/galleria/galleria10s.jpg",
      "Description for Image 10", "Title 10"));
      this.photos.add(new Photo("demo/images/galleria/galleria11.jpg", "demo/images/galleria/galleria11s.jpg",
      "Description for Image 11", "Title 11"));
      this.photos.add(new Photo("demo/images/galleria/galleria12.jpg", "demo/images/galleria/galleria12s.jpg",
      "Description for Image 12", "Title 12"));
      this.photos.add(new Photo("demo/images/galleria/galleria13.jpg", "demo/images/galleria/galleria13s.jpg",
      "Description for Image 13", "Title 13"));
      this.photos.add(new Photo("demo/images/galleria/galleria14.jpg", "demo/images/galleria/galleria14s.jpg",
      "Description for Image 14", "Title 14"));
      this.photos.add(new Photo("demo/images/galleria/galleria15.jpg", "demo/images/galleria/galleria15s.jpg",
      "Description for Image 15", "Title 15"));
  }

  public List<Photo> getPhotos() {
    return this.photos;
  }
}
