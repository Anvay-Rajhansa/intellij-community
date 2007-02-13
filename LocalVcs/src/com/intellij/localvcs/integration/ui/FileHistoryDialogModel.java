package com.intellij.localvcs.integration.ui;

import com.intellij.localvcs.*;
import com.intellij.localvcs.integration.FileReverter;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.vfs.VirtualFile;

import java.io.IOException;
import java.util.List;

public class FileHistoryDialogModel extends HistoryDialogModel {
  private FileDocumentManager myDocumentManager;

  public FileHistoryDialogModel(VirtualFile f, ILocalVcs vcs, FileDocumentManager dm) {
    super(f, vcs);
    myDocumentManager = dm;
  }

  @Override
  protected void addCurrentVersionTo(List<Label> l) {
    if (getCurrentContent().equals(getVcsContent())) return;
    l.add(new CurrentLabel());
  }

  private Content getCurrentContent() {
    // todo review byte conversion
    byte[] b = myDocumentManager.getDocument(myFile).getText().getBytes();
    return new ByteContent(b);
  }

  private Content getVcsContent() {
    return getVcsEntry().getContent();
  }

  private Entry getVcsEntry() {
    return myVcs.getEntry(myFile.getPath());
  }

  public Content getLeftContent() {
    return getLeftLabel().getEntry().getContent();
  }

  public Content getRightContent() {
    return getRightLabel().getEntry().getContent();
  }

  public void revert() throws IOException {
    FileReverter.revert(myFile, getLeftEntry());
  }

  private class CurrentLabel extends Label {
    public CurrentLabel() {
      super(null, null, null, null);
    }

    @Override
    public String getName() {
      return "current";
    }

    @Override
    public long getTimestamp() {
      throw new RuntimeException("not yet implemented");
    }

    @Override
    public Entry getEntry() {
      // todo what about timestamp?
      // todo review content stuff

      // todo test copying
      // todo it seems ugly
      Entry e = getVcsEntry().copy();
      e.changeContent(getCurrentContent(), null);
      return e;
    }
  }
}
