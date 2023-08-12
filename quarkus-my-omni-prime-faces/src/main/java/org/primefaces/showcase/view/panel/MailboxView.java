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
package org.primefaces.showcase.view.panel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.showcase.domain.Mail;

@Named
@ViewScoped
public class MailboxView implements Serializable {

  private TreeNode mailboxes;

  private List<Mail> mails;

  private Mail mail;

  private TreeNode mailbox;

  @PostConstruct
  public void init() {
    this.mailboxes = new DefaultTreeNode("root", null);

    TreeNode inbox = new DefaultTreeNode("i", "Inbox", this.mailboxes);
    TreeNode sent = new DefaultTreeNode("s", "Sent", this.mailboxes);
    TreeNode trash = new DefaultTreeNode("t", "Trash", this.mailboxes);
    TreeNode junk = new DefaultTreeNode("j", "Junk", this.mailboxes);

    TreeNode gmail = new DefaultTreeNode("Gmail", inbox);
    TreeNode hotmail = new DefaultTreeNode("Hotmail", inbox);

    this.mails = new ArrayList<Mail>();
    this.mails.add(new Mail("optimus@primefaces.com", "Team Meeting", "Meeting to discuss roadmap", new Date()));
    this.mails.add(new Mail("spammer@spammer.com", "You've won Lottery", "Send me your credit card info to claim", new Date()));
    this.mails.add(new Mail("spammer@spammer.com", "Your email has won", "Click the exe file to claim", new Date()));
    this.mails.add(new Mail("primefaces-commits", "[primefaces] r4491 - Layout mailbox sample", "Revision:4490 Author:cagatay.civici", new Date()));
  }

  public TreeNode getMailboxes() {
    return this.mailboxes;
  }

  public List<Mail> getMails() {
    return this.mails;
  }

  public Mail getMail() {
    return this.mail;
  }

  public void setMail(Mail mail) {
    this.mail = mail;
  }

  public TreeNode getMailbox() {
    return this.mailbox;
  }

  public void setMailbox(TreeNode mailbox) {
    this.mailbox = mailbox;
  }

  public void send() {
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mail Sent!"));
  }
}
