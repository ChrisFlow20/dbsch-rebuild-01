package com.wisecoders.dbs.dialogs.git.fx;

import com.wisecoders.dbs.dialogs.git.credentials.GitCredentials;
import com.wisecoders.dbs.dialogs.git.model.GitFile;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.store.ProjectLoader;
import com.wisecoders.dbs.sys.Log;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryBuilder;
import org.eclipse.jgit.revplot.PlotCommitList;
import org.eclipse.jgit.revplot.PlotWalk;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.TreeFilter;

public class GitRepo {
  public final Repository a;
  
  public final File b;
  
  public final GitCredentials c = new GitCredentials();
  
  public final ArrayList d;
  
  public final Map e;
  
  public static GitRepo a(Project paramProject) {
    if (paramProject == null || !paramProject.hasFile() || !paramProject.getFile().exists())
      throw new IOException("Please save the design model to file."); 
    RepositoryBuilder repositoryBuilder = (RepositoryBuilder)((RepositoryBuilder)(new RepositoryBuilder()).readEnvironment()).findGitDir(paramProject.getFile());
    if (repositoryBuilder.getGitDir() != null) {
      try {
        if (a(repositoryBuilder))
          repositoryBuilder.readEnvironment(); 
      } catch (IOException iOException) {
        Log.b(iOException);
      } 
      Repository repository = repositoryBuilder.build();
      return new GitRepo(repository, paramProject.getFile());
    } 
    throw new IOException("No repository found");
  }
  
  private static boolean a(RepositoryBuilder paramRepositoryBuilder) {
    File file = new File(paramRepositoryBuilder.getGitDir().getParentFile(), ".gitattributes");
    if (file.exists()) {
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
      try {
        String str;
        while ((str = bufferedReader.readLine()) != null) {
          if (str.trim().startsWith("*.dbs")) {
            boolean bool = true;
            bufferedReader.close();
            return bool;
          } 
        } 
        bufferedReader.close();
      } catch (Throwable throwable) {
        try {
          bufferedReader.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        } 
        throw throwable;
      } 
    } else if (!file.createNewFile()) {
      return false;
    } 
    Files.write(file.toPath(), "\n# DbSchema Model File - do binary diff\n*.dbs binary\n*.dbs.bak binary\n".getBytes(), new OpenOption[] { StandardOpenOption.APPEND });
    return true;
  }
  
  public String a(File paramFile) {
    return this.a.getWorkTree().toPath().relativize(paramFile.toPath()).toString();
  }
  
  public String a(String paramString, RevCommit paramRevCommit) {
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    a(paramString, paramRevCommit, byteArrayOutputStream);
    return byteArrayOutputStream.toString(StandardCharsets.UTF_8);
  }
  
  public void a(String paramString, RevCommit paramRevCommit, OutputStream paramOutputStream) {
    TreeWalk treeWalk = new TreeWalk(this.a);
    try {
      treeWalk.addTree((AnyObjectId)paramRevCommit.getTree());
      treeWalk.setRecursive(true);
      while (treeWalk.next()) {
        if (treeWalk.getPathString().equals(paramString)) {
          ObjectId objectId = treeWalk.getObjectId(0);
          ObjectLoader objectLoader = this.a.open((AnyObjectId)objectId);
          objectLoader.copyTo(paramOutputStream);
          treeWalk.close();
          return;
        } 
      } 
      throw new IllegalStateException("File '" + paramString + "' not found in commit '" + paramRevCommit.getFullMessage() + "'.");
    } catch (Throwable throwable) {
      try {
        treeWalk.close();
      } catch (Throwable throwable1) {
        throwable.addSuppressed(throwable1);
      } 
      throw throwable;
    } 
  }
  
  public List a(String paramString, boolean paramBoolean) {
    ArrayList<RevCommit> arrayList = new ArrayList();
    Git git = new Git(this.a);
    try {
      for (RevCommit revCommit : paramBoolean ? git.log().call() : git.log().addPath(paramString).call())
        arrayList.add(revCommit); 
      git.close();
    } catch (Throwable throwable) {
      try {
        git.close();
      } catch (Throwable throwable1) {
        throwable.addSuppressed(throwable1);
      } 
      throw throwable;
    } 
    return arrayList;
  }
  
  public Project b(String paramString, RevCommit paramRevCommit) {
    String str = a(paramString, paramRevCommit);
    Log.c("Loaded project from " + String.valueOf(paramRevCommit));
    Log.c(str);
    return a(str);
  }
  
  public Project a(String paramString) {
    ProjectLoader projectLoader = new ProjectLoader();
    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(paramString.getBytes(StandardCharsets.UTF_8));
    try {
      projectLoader.parse(byteArrayInputStream);
      byteArrayInputStream.close();
    } catch (Throwable throwable) {
      try {
        byteArrayInputStream.close();
      } catch (Throwable throwable1) {
        throwable.addSuppressed(throwable1);
      } 
      throw throwable;
    } 
    return projectLoader.getProject();
  }
  
  public byte[] b(String paramString) {
    ObjectId objectId = this.a.resolve(paramString);
    ObjectLoader objectLoader = this.a.open((AnyObjectId)objectId);
    return objectLoader.getBytes();
  }
  
  public PlotCommitList a(String paramString, TreeFilter paramTreeFilter, int paramInt, ProgressMonitor paramProgressMonitor) {
    PlotCommitList plotCommitList = new PlotCommitList();
    PlotWalk plotWalk = new PlotWalk(this.a);
    try {
      if (paramString != null) {
        ObjectId objectId = this.a.resolve(paramString);
        if (objectId != null) {
          RevCommit revCommit = plotWalk.parseCommit((AnyObjectId)objectId);
          plotWalk.markStart(revCommit);
        } 
      } 
      if (paramTreeFilter != null)
        plotWalk.setTreeFilter(paramTreeFilter); 
      if (paramTreeFilter == TreeFilter.ALL) {
        List list = this.a.getRefDatabase().getRefs();
        byte b = 0;
        if (paramProgressMonitor != null)
          paramProgressMonitor.beginTask("Collecting revision", list.size()); 
        for (Ref ref : list) {
          plotWalk.markStart(plotWalk.parseCommit((AnyObjectId)ref.getObjectId()));
          if (paramProgressMonitor != null) {
            b++;
            paramProgressMonitor.update(b);
          } 
        } 
      } 
      plotCommitList.source((RevWalk)plotWalk);
      plotCommitList.fillTo((paramInt == -1) ? Integer.MAX_VALUE : paramInt);
      plotWalk.dispose();
      if (paramProgressMonitor != null)
        paramProgressMonitor.endTask(); 
      PlotCommitList plotCommitList1 = plotCommitList;
      plotWalk.close();
      return plotCommitList1;
    } catch (Throwable throwable) {
      try {
        plotWalk.close();
      } catch (Throwable throwable1) {
        throwable.addSuppressed(throwable1);
      } 
      throw throwable;
    } 
  }
  
  public List a(RevCommit paramRevCommit) {
    TreeWalk treeWalk = new TreeWalk(this.a);
    treeWalk.setRecursive(true);
    treeWalk.setFilter(TreeFilter.ANY_DIFF);
    ArrayList arrayList = new ArrayList();
    if (paramRevCommit.getParentCount() > 0) {
      treeWalk.reset(new AnyObjectId[] { (AnyObjectId)paramRevCommit.getParent(0).getTree().getId(), (AnyObjectId)paramRevCommit.getTree().getId() });
      List list = DiffEntry.scan(treeWalk);
      list.forEach(paramDiffEntry -> paramList.add(new GitFile(paramDiffEntry.getNewPath(), 8)));
    } 
    return arrayList;
  }
  
  public static String a(MergeResult paramMergeResult) {
    StringBuilder stringBuilder = new StringBuilder();
    String str = "\n    ";
    stringBuilder.append(paramMergeResult);
    if (paramMergeResult.getMergeStatus().isSuccessful()) {
      if (paramMergeResult.getNewHead() != null)
        stringBuilder.append("\nNew head: ").append(paramMergeResult.getNewHead().getName()); 
      if (paramMergeResult.getBase() != null)
        stringBuilder.append("\nBase: ").append(paramMergeResult.getBase().getName()); 
      String str1 = Arrays.<ObjectId>stream(paramMergeResult.getMergedCommits()).map(paramObjectId -> ObjectId.toString(paramObjectId)).collect(Collectors.joining("\n    "));
      stringBuilder.append("\nMerged:").append("\n    ").append(str1);
    } else {
      if (paramMergeResult.getConflicts() != null) {
        String str1 = String.join("\n    ", paramMergeResult.getConflicts().keySet());
        stringBuilder.append("\nConflict:").append("\n    ").append(str1);
      } 
      if (paramMergeResult.getFailingPaths() != null) {
        String str1 = String.join("\n    ", paramMergeResult.getFailingPaths().keySet());
        stringBuilder.append("\nFailed:").append("\n    ").append(str1);
      } 
    } 
    return stringBuilder.toString();
  }
  
  public GitRepo(Repository paramRepository, File paramFile) {
    this.d = new ArrayList();
    this.e = new HashMap<>();
    this.a = paramRepository;
    this.b = paramFile;
    this.c.a(this.a.getConfig().getString("remote", "origin", "url"));
  }
  
  public void a(Set paramSet, int paramInt) {
    if (paramSet != null)
      for (String str : paramSet)
        a(str, paramInt);  
  }
  
  public GitFile a(String paramString, int paramInt) {
    for (GitFile gitFile1 : this.d) {
      if (gitFile1.i.equals(paramString)) {
        gitFile1.a(paramInt);
        return gitFile1;
      } 
    } 
    GitFile gitFile = new GitFile(paramString, paramInt);
    this.d.add(gitFile);
    return gitFile;
  }
  
  public void a() {
    this.d.sort(Comparator.comparing(GitFile::e));
  }
  
  public void b(MergeResult paramMergeResult) {
    if (paramMergeResult.getConflicts() != null)
      for (Map.Entry entry : paramMergeResult.getConflicts().entrySet()) {
        String str = (String)entry.getKey();
        try {
          String str1 = a(this.a.resolve("HEAD:" + str));
          String str2 = a(this.a.resolve("MERGE_HEAD:" + str));
          this.e.put(str, new GitRepo$ConflictVersions(str1, str2));
        } catch (Exception exception) {
          Log.b(exception);
        } 
      }  
  }
  
  private String a(ObjectId paramObjectId) {
    ObjectLoader objectLoader = this.a.open((AnyObjectId)paramObjectId);
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    objectLoader.copyTo(byteArrayOutputStream);
    return byteArrayOutputStream.toString(StandardCharsets.UTF_8);
  }
  
  public static String a(Ref paramRef) {
    if (paramRef.getName().startsWith("refs/heads/"))
      return "Local"; 
    if (paramRef.getName().startsWith("refs/remotes/"))
      return "Remote " + paramRef.getName().split("/")[2]; 
    return paramRef.getName();
  }
  
  public static String b(Ref paramRef) {
    int i;
    return ((i = paramRef.getName().lastIndexOf("/")) > -1) ? paramRef.getName().substring(i + 1) : paramRef.getName();
  }
}
