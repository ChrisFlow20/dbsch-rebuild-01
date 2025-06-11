package com.wisecoders.dbs.dbms.sync.engine.nodes;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.data.model.result.Result;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AbstractDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterScript;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterStatement;
import com.wisecoders.dbs.dbms.sync.engine.diffs.ColumnDataTypeDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.ColumnDefaultValueDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.ColumnExistsDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.CommentDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.ConstraintExistsDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.ForeignKeyCascadeDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.ForeignKeyColumnsDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.ForeignKeyExistsDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.IndexExistsDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.IndexUniquenessDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.MaterializedViewExistsDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.MaterializedViewScriptDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.RenameDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.SchemaExistsDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.SequenceExistsDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.SequenceOptionsDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.SqlExistsDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.SqlScriptDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.TableConstraintDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.TableExistsDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.UserDataTypeExistsDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.UserDataTypeScriptDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.ViewExistsDiff;
import com.wisecoders.dbs.dbms.sync.engine.diffs.ViewScriptDiff;
import com.wisecoders.dbs.dbms.sync.model.SyncFilter;
import com.wisecoders.dbs.dbms.sync.model.SyncUtil;
import com.wisecoders.dbs.diagram.fx.GenericLayoutPane;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Folder;
import com.wisecoders.dbs.schema.AttributeSpec;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.Constraint;
import com.wisecoders.dbs.schema.DbUnit;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.MaterializedView;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.SchemaMapping;
import com.wisecoders.dbs.schema.Sequence;
import com.wisecoders.dbs.schema.Sql;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.schema.TableDependency;
import com.wisecoders.dbs.schema.UserDataType;
import com.wisecoders.dbs.schema.View;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@DoNotObfuscate
public class SyncPair implements SyncDiff {
  private final List a = new ArrayList();
  
  private final List b = new ArrayList();
  
  public final SchemaMapping mapping;
  
  public final SyncPair parent;
  
  public final AbstractUnit left;
  
  public final AbstractUnit right;
  
  public final AbstractUnit leftParent;
  
  public final AbstractUnit rightParent;
  
  private boolean c = false;
  
  private final boolean d;
  
  private final boolean e;
  
  private SyncAction f = SyncAction.noAction;
  
  public TableDependency dependency;
  
  public SyncPair(AbstractUnit paramAbstractUnit1, SchemaMapping paramSchemaMapping, AbstractUnit paramAbstractUnit2) {
    this.parent = null;
    this.left = paramAbstractUnit1;
    this.right = paramAbstractUnit2;
    this.leftParent = null;
    this.rightParent = null;
    this.d = true;
    this.mapping = paramSchemaMapping;
    if (paramAbstractUnit1 instanceof Project) {
      Project project = (Project)paramAbstractUnit1;
      if (paramAbstractUnit2 instanceof Project) {
        Project project1 = (Project)paramAbstractUnit2;
        this.e = ((Dbms.get(project.getDbId())).alterColumn.e() == (Dbms.get(project1.getDbId())).alterColumn.e());
        ArrayList<Schema> arrayList = new ArrayList();
        for (Schema schema1 : project1.schemas) {
          Schema schema2 = paramSchemaMapping.getLocalSchema(project, schema1.getNameWithCatalog());
          arrayList.add(schema2);
          this.a.add(new SyncPair(project, schema2, project1, schema1, true, paramSchemaMapping));
        } 
        for (Schema schema : project.schemas) {
          if (!arrayList.contains(schema))
            this.a.add(new SyncPair(project, schema, project1, null, true, paramSchemaMapping)); 
        } 
        filter(Dbms.get(project1.getDbId()).getSynchronizationFilter());
        return;
      } 
    } 
    this.e = true;
  }
  
  public SyncPair(AbstractUnit paramAbstractUnit1, AbstractUnit paramAbstractUnit2) {
    this(null, null, paramAbstractUnit1, null, paramAbstractUnit2, true, null);
  }
  
  public SyncPair(AbstractUnit paramAbstractUnit1, AbstractUnit paramAbstractUnit2, AbstractUnit paramAbstractUnit3, AbstractUnit paramAbstractUnit4, boolean paramBoolean, SchemaMapping paramSchemaMapping) {
    this(null, paramAbstractUnit1, paramAbstractUnit2, paramAbstractUnit3, paramAbstractUnit4, paramBoolean, paramSchemaMapping);
  }
  
  private SyncPair(SyncPair paramSyncPair, AbstractUnit paramAbstractUnit1, AbstractUnit paramAbstractUnit2, AbstractUnit paramAbstractUnit3, AbstractUnit paramAbstractUnit4, boolean paramBoolean, SchemaMapping paramSchemaMapping) {
    this.parent = paramSyncPair;
    this.left = paramAbstractUnit2;
    this.right = paramAbstractUnit4;
    this.leftParent = paramAbstractUnit1;
    this.rightParent = paramAbstractUnit3;
    this.d = paramBoolean;
    this.e = true;
    this.mapping = paramSchemaMapping;
    synchronize();
  }
  
  public SchemaMapping getMapping() {
    if (this.mapping != null)
      return this.mapping; 
    if (this.parent != null)
      return this.parent.getMapping(); 
    return null;
  }
  
  public void synchronize() {
    this.b.clear();
    this.a.clear();
    boolean bool = (getUnit() instanceof Index || getUnit() instanceof ForeignKey || getUnit() instanceof Constraint || getUnit() instanceof Sequence || getUnit() instanceof UserDataType || getUnit() instanceof View) ? true : false;
    if (this.left == null || this.right == null) {
      if (this.left instanceof Schema || this.right instanceof Schema) {
        this.b.add(new SchemaExistsDiff(this));
      } else if (this.left instanceof Table || this.right instanceof Table) {
        this.b.add(new TableExistsDiff(this));
      } else if (this.left instanceof Column || this.right instanceof Column) {
        this.b.add(new ColumnExistsDiff(this));
      } else if (this.left instanceof Index || this.right instanceof Index) {
        this.b.add(new IndexExistsDiff(this));
      } else if (this.left instanceof ForeignKey || this.right instanceof ForeignKey) {
        ForeignKeyExistsDiff foreignKeyExistsDiff = new ForeignKeyExistsDiff(this);
        if (this.c || foreignKeyExistsDiff.targetEntityExistsInLeftSide())
          this.b.add(foreignKeyExistsDiff); 
      } else if (this.left instanceof Constraint || this.right instanceof Constraint) {
        this.b.add(new ConstraintExistsDiff(this));
      } else if (this.left instanceof MaterializedView || this.right instanceof MaterializedView) {
        this.b.add(new MaterializedViewExistsDiff(this));
      } else if (this.left instanceof View || this.right instanceof View) {
        this.b.add(new ViewExistsDiff(this));
      } else if (this.left instanceof UserDataType || this.right instanceof UserDataType) {
        this.b.add(new UserDataTypeExistsDiff(this));
      } else if (this.left instanceof Sql || this.right instanceof Sql) {
        this.b.add(new SqlExistsDiff(this));
      } else if (this.left instanceof Sequence || this.right instanceof Sequence) {
        this.b.add(new SequenceExistsDiff(this));
      } 
    } else if (bool && !this.left.sameAs(this.right, false)) {
      this.a.add(new SyncPair(this, this.leftParent, null, this.rightParent, this.right, this.d, null));
      this.a.add(new SyncPair(this, this.leftParent, this.left, this.rightParent, null, this.d, null));
    } else {
      boolean bool1 = false;
      AbstractUnit abstractUnit = this.left;
      if (abstractUnit instanceof Column) {
        Column column = (Column)abstractUnit;
        abstractUnit = this.right;
        if (column.getSpec() == AttributeSpec.normal && abstractUnit.getSpec() == AttributeSpec.normal) {
          if (!SyncUtil.a(column.getIdentity(), abstractUnit.getIdentity()) || column
            .isUnsigned() != abstractUnit.isUnsigned() || 
            !column.isUsingSameDataType((Column)abstractUnit) || column
            .isMandatory() != abstractUnit.isMandatory())
            this.b.add(new ColumnDataTypeDiff(this)); 
          if (!column.defaultValuesAreSimilar((Column)abstractUnit))
            this.b.add(new ColumnDefaultValueDiff(this)); 
        } 
      } else {
        abstractUnit = this.left;
        if (abstractUnit instanceof Index) {
          Index index = (Index)abstractUnit;
          abstractUnit = this.right;
          if (!index.getType().sameAs(abstractUnit.getType())) {
            this.b.add(new IndexUniquenessDiff(this));
            bool1 = true;
          } 
        } else {
          abstractUnit = this.left;
          if (abstractUnit instanceof ForeignKey) {
            ForeignKey foreignKey = (ForeignKey)abstractUnit;
            abstractUnit = this.right;
            if (!foreignKey.usesSameColumns((ForeignKey)abstractUnit)) {
              this.b.add(new ForeignKeyColumnsDiff(this));
              bool1 = true;
            } else if (!foreignKey.getDeleteAction().compatibleWith(abstractUnit.getDeleteAction()) || 
              !foreignKey.getUpdateAction().compatibleWith(abstractUnit.getUpdateAction())) {
              this.b.add(new ForeignKeyCascadeDiff(this));
              bool1 = true;
            } 
          } else {
            abstractUnit = this.left;
            if (abstractUnit instanceof Constraint) {
              Constraint constraint = (Constraint)abstractUnit;
              abstractUnit = this.right;
              if (!StringUtil.stringsAreEqualIgnoreCasesAndSpaces(constraint.getText(), abstractUnit.getText())) {
                this.b.add(new TableConstraintDiff(this));
                bool1 = true;
              } 
            } else {
              abstractUnit = this.left;
              if (abstractUnit instanceof Sequence) {
                Sequence sequence = (Sequence)abstractUnit;
                abstractUnit = this.right;
                if (StringUtil.isFilledTrim(abstractUnit.getOptions()) && !DbUnit.optionsAreEqual(sequence.getOptions(), abstractUnit.getOptions()))
                  this.b.add(new SequenceOptionsDiff(this)); 
              } else {
                abstractUnit = this.left;
                if (abstractUnit instanceof MaterializedView) {
                  MaterializedView materializedView = (MaterializedView)abstractUnit;
                  abstractUnit = this.right;
                  if ((Dbms.get(materializedView)).scriptComparator.a(materializedView.getScript(), abstractUnit.getScript()))
                    this.b.add(new MaterializedViewScriptDiff(this)); 
                } else {
                  abstractUnit = this.left;
                  if (abstractUnit instanceof View) {
                    View view = (View)abstractUnit;
                    abstractUnit = this.right;
                    if ((Dbms.get(view)).scriptComparator.a(view.getScript(), abstractUnit.getScript())) {
                      this.b.add(new ViewScriptDiff(this));
                      bool1 = true;
                    } 
                  } else {
                    abstractUnit = this.left;
                    if (abstractUnit instanceof Sql) {
                      Sql sql = (Sql)abstractUnit;
                      abstractUnit = this.right;
                      if ((Dbms.get(sql.getDbId())).scriptComparator.a(sql.getText(), abstractUnit.getText())) {
                        this.b.add(new SqlScriptDiff(this));
                        bool1 = true;
                      } 
                    } else {
                      abstractUnit = this.left;
                      if (abstractUnit instanceof UserDataType) {
                        UserDataType userDataType = (UserDataType)abstractUnit;
                        abstractUnit = this.right;
                        if ((Dbms.get(userDataType)).scriptComparator.a(userDataType.getScript(), abstractUnit.getScript()))
                          this.b.add(new UserDataTypeScriptDiff(this)); 
                      } 
                    } 
                  } 
                } 
              } 
            } 
          } 
        } 
      } 
      if (!bool1 && !this.left.getName().equals(this.right.getName()) && !(this.left instanceof Project)) {
        RenameDiff renameDiff = new RenameDiff(this);
        renameDiff.setRenameDoneByOtherDiff(bool1);
        this.b.add(renameDiff);
      } 
      if (this.e && !StringUtil.stringsAreEqualIgnoreCasesAndSpaces(this.left.getComment(), this.right.getComment()))
        this.b.add(new CommentDiff(this)); 
    } 
    if (this.d && 
      this.left != null && this.left.getSyncFolders() != null && this.right != null && this.right.getSyncFolders() != null)
      for (Folder folder1 : this.left.getSyncFolders()) {
        Folder folder2 = null;
        for (Folder folder : this.right.getSyncFolders()) {
          if (folder.childClass == folder1.childClass)
            folder2 = folder; 
        } 
        if (folder2 != null) {
          ArrayList<AbstractUnit> arrayList = new ArrayList();
          for (AbstractUnit abstractUnit1 : folder2) {
            AbstractUnit abstractUnit2 = null;
            for (AbstractUnit abstractUnit : folder1) {
              if (abstractUnit1.sameAs(abstractUnit, false))
                abstractUnit2 = abstractUnit; 
            } 
            if (abstractUnit2 == null)
              arrayList.add(abstractUnit1); 
          } 
          for (AbstractUnit abstractUnit1 : folder1) {
            AbstractUnit abstractUnit2 = null;
            for (AbstractUnit abstractUnit : folder2) {
              if (abstractUnit1.sameAs(abstractUnit, false) && (
                abstractUnit2 == null || StringUtil.areEqual(abstractUnit1.getName(), abstractUnit.getName())))
                abstractUnit2 = abstractUnit; 
            } 
            boolean bool1 = false;
            if (abstractUnit2 == null)
              for (Iterator<AbstractUnit> iterator = arrayList.iterator(); iterator.hasNext(); ) {
                AbstractUnit abstractUnit = iterator.next();
                if (abstractUnit1.sameAs(abstractUnit, true)) {
                  bool1 = true;
                  iterator.remove();
                  this.a.add(new SyncPair(this, this.left, abstractUnit1, abstractUnit, abstractUnit, this.d, null));
                  break;
                } 
              }  
            if (!bool1) {
              SyncPair syncPair = new SyncPair(this, this.left, abstractUnit1, this.right, abstractUnit2, this.d, null);
              if (syncPair.hasDifferences())
                this.a.add(syncPair); 
            } 
          } 
          for (AbstractUnit abstractUnit : arrayList) {
            SyncPair syncPair = new SyncPair(this, this.left, null, this.right, abstractUnit, this.d, null);
            if (syncPair.hasDifferences())
              this.a.add(syncPair); 
          } 
        } 
      }  
  }
  
  public boolean hasDifferences() {
    if (!this.b.isEmpty())
      return true; 
    for (SyncPair syncPair : this.a) {
      if (syncPair.hasDifferences())
        return true; 
    } 
    return false;
  }
  
  public int getDiffCount() {
    int i = this.b.size();
    for (SyncPair syncPair : this.a)
      i += syncPair.getDiffCount(); 
    return i;
  }
  
  public void setAction(SyncAction paramSyncAction, boolean paramBoolean) {
    this.f = paramSyncAction;
    for (AbstractDiff abstractDiff : this.b)
      abstractDiff.setAction(paramSyncAction, true); 
    if (paramBoolean)
      for (SyncPair syncPair : this.a)
        syncPair.setAction(paramSyncAction, paramBoolean);  
  }
  
  public List getChildrenPairs() {
    return this.a;
  }
  
  public List getDifferences() {
    return this.b;
  }
  
  public boolean hasNodesWithAction(SyncAction paramSyncAction) {
    if (this.f == paramSyncAction)
      return true; 
    for (AbstractDiff abstractDiff : this.b) {
      if (abstractDiff.getAction() == paramSyncAction)
        return true; 
    } 
    for (SyncPair syncPair : this.a) {
      if (syncPair.hasNodesWithAction(paramSyncAction))
        return true; 
    } 
    return false;
  }
  
  public int actionWillDrop(SyncAction paramSyncAction, SyncSide paramSyncSide, boolean paramBoolean) {
    AbstractUnit abstractUnit1 = getUnit(paramSyncSide);
    AbstractUnit abstractUnit2 = getUnit(paramSyncSide.opposite());
    int i = 0;
    for (AbstractDiff abstractDiff : this.b) {
      if (abstractDiff instanceof com.wisecoders.dbs.dbms.sync.engine.diffs.AbstractExistsDiff && abstractUnit2 == null && abstractDiff.getAction() == paramSyncAction && (!paramBoolean || abstractUnit1 instanceof Table || abstractUnit1 instanceof Column))
        i++; 
    } 
    for (SyncPair syncPair : this.a)
      i += syncPair.actionWillDrop(paramSyncAction, paramSyncSide, paramBoolean); 
    return i;
  }
  
  public String toString() {
    return String.valueOf(getUnit());
  }
  
  public String toString(int paramInt) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(a(paramInt));
    if (getUnit() != null)
      stringBuilder.append(getUnit().getSymbolicName()); 
    stringBuilder.append(" ").append(this.left).append(" ~ ").append(this.right);
    for (AbstractDiff abstractDiff : this.b)
      stringBuilder.append("\n").append(a(paramInt + 1)).append("Diff: ")
        .append(abstractDiff.getDiffString(SyncSide.left)).append(" ~ ").append(abstractDiff.getDiffString(SyncSide.right)); 
    stringBuilder.append("\n");
    for (SyncPair syncPair : this.a)
      stringBuilder.append(syncPair.toString(paramInt + 1)); 
    return stringBuilder.toString();
  }
  
  private String a(int paramInt) {
    return "\t".repeat(Math.max(0, paramInt));
  }
  
  public SyncAction getAction() {
    return this.f;
  }
  
  public void toSql(String paramString, SyncAction paramSyncAction, SyncSide paramSyncSide, PrintWriter paramPrintWriter) {
    for (AlterStatement alterStatement : (generateCommitScript(paramString, paramSyncAction, paramSyncSide)).statements) {
      paramPrintWriter.write(alterStatement.toString());
      paramPrintWriter.write(";\n\n");
    } 
  }
  
  public void commitIntoDatabase(Connector paramConnector, SyncSide paramSyncSide, SyncAction paramSyncAction) {
    if (paramConnector != null)
      try {
        Envoy envoy = paramConnector.startEnvoy("Synchronization commit");
        try {
          a(envoy, paramSyncSide, paramSyncAction);
          envoy.p();
          if (envoy != null)
            envoy.close(); 
        } catch (Throwable throwable) {
          if (envoy != null)
            try {
              envoy.close();
            } catch (Throwable throwable1) {
              throwable.addSuppressed(throwable1);
            }  
          throw throwable;
        } 
      } catch (Exception exception) {
        Log.f(exception.toString());
        throw exception;
      }  
  }
  
  void a(Envoy paramEnvoy, SyncSide paramSyncSide, SyncAction paramSyncAction) {
    AlterScript alterScript = generateCommitScript(paramEnvoy.a.dbId, paramSyncAction, paramSyncSide);
    for (AlterStatement alterStatement : alterScript.statements) {
      Log.c("Sync: " + alterStatement.toString());
      if (paramEnvoy.a.isReadOnly())
        throw new SQLException("Connection is Read Only. Schema changes are not allowed."); 
      if (Sys.B.readOnly.b())
        throw new SQLException("DbSchema is Read Only. See Settings / Connectivity / Read Only."); 
      String str = alterStatement.toString();
      if (str.toLowerCase().startsWith("select")) {
        paramEnvoy.b(str, new Result());
        continue;
      } 
      paramEnvoy.g(alterStatement.toString());
    } 
  }
  
  public AlterScript generateCommitScript(String paramString, SyncAction paramSyncAction, SyncSide paramSyncSide) {
    return generateCommitScript(paramString, paramSyncAction, paramSyncSide, (Dbms.get(paramString)).foreignKeysInline.b());
  }
  
  public AlterScript generateCommitScript(String paramString, SyncAction paramSyncAction, SyncSide paramSyncSide, boolean paramBoolean) {
    a(paramBoolean);
    AlterScript alterScript = new AlterScript(paramString);
    for (AbstractDiff abstractDiff : this.b) {
      if (paramSyncAction == null || abstractDiff.getAction() == paramSyncAction)
        alterScript.addAll(abstractDiff.commitInto(paramString, paramSyncSide, paramBoolean)); 
    } 
    for (SyncPair syncPair : this.a)
      alterScript.addAll(syncPair.generateCommitScript(paramString, paramSyncAction, paramSyncSide, paramBoolean)); 
    alterScript.sort();
    return alterScript;
  }
  
  public void mergeInto(SyncSide paramSyncSide, SyncAction paramSyncAction) {
    mergeInto(paramSyncSide, paramSyncAction, null);
  }
  
  public void mergeInto(SyncSide paramSyncSide, SyncAction paramSyncAction, GenericLayoutPane paramGenericLayoutPane) {
    a(paramSyncSide, paramSyncAction, paramGenericLayoutPane, false);
    a(paramSyncSide, paramSyncAction, paramGenericLayoutPane, true);
    AbstractUnit abstractUnit = (getParent(paramSyncSide) != null) ? getParent(paramSyncSide) : getUnit(paramSyncSide);
    if (abstractUnit != null)
      abstractUnit.refresh(); 
    if (paramGenericLayoutPane != null)
      paramGenericLayoutPane.a(); 
  }
  
  private void a(SyncSide paramSyncSide, SyncAction paramSyncAction, GenericLayoutPane paramGenericLayoutPane, boolean paramBoolean) {
    for (AbstractDiff abstractDiff : this.b) {
      if (paramSyncAction == null || paramSyncAction == abstractDiff.getAction()) {
        if (paramBoolean) {
          abstractDiff.mergeFinal(paramSyncSide, paramGenericLayoutPane);
          continue;
        } 
        abstractDiff.merge(paramSyncSide, paramGenericLayoutPane);
      } 
    } 
    for (SyncPair syncPair : this.a)
      syncPair.a(paramSyncSide, paramSyncAction, paramGenericLayoutPane, paramBoolean); 
  }
  
  public AbstractUnit getUnit(SyncSide paramSyncSide) {
    switch (SyncPair$1.a[paramSyncSide.ordinal()]) {
      default:
        throw new IncompatibleClassChangeError();
      case 1:
      
      case 2:
        break;
    } 
    return 
      
      this.right;
  }
  
  public AbstractUnit getUnit() {
    return (this.left != null) ? this.left : this.right;
  }
  
  public AbstractUnit getParent(SyncSide paramSyncSide) {
    switch (SyncPair$1.a[paramSyncSide.ordinal()]) {
      default:
        throw new IncompatibleClassChangeError();
      case 1:
      
      case 2:
        break;
    } 
    return 
      
      this.rightParent;
  }
  
  public void filter(SyncFilter paramSyncFilter) {
    if (paramSyncFilter != null) {
      Objects.requireNonNull(paramSyncFilter);
      this.b.removeIf(paramSyncFilter::rejectDiff);
      for (Iterator<SyncPair> iterator = this.a.iterator(); iterator.hasNext(); ) {
        SyncPair syncPair = iterator.next();
        syncPair.filter(paramSyncFilter);
        if (!syncPair.hasDifferences())
          iterator.remove(); 
      } 
    } 
  }
  
  public AbstractDiff getNodeDiff() {
    return (this.a.isEmpty() && this.b.size() == 1) ? this.b.get(0) : null;
  }
  
  private void a(boolean paramBoolean) {
    ArrayList<Table> arrayList = new ArrayList();
    if (this.parent == null && a() != null) {
      arrayList.add(a().getTable());
      this.dependency = new TableDependency(arrayList, paramBoolean);
    } else {
      for (SyncPair syncPair : this.a) {
        TableExistsDiff tableExistsDiff = syncPair.a();
        if (tableExistsDiff != null)
          arrayList.add(tableExistsDiff.getTable()); 
      } 
      if (!arrayList.isEmpty()) {
        TableDependency tableDependency = new TableDependency(arrayList, paramBoolean);
        this.a.sort((paramSyncPair1, paramSyncPair2) -> {
              TableExistsDiff tableExistsDiff1 = paramSyncPair1.a();
              TableExistsDiff tableExistsDiff2 = paramSyncPair2.a();
              return paramTableDependency.compareByCreationOrder((tableExistsDiff1 != null) ? tableExistsDiff1.getTable() : null, (tableExistsDiff2 != null) ? tableExistsDiff2.getTable() : null);
            });
        for (SyncPair syncPair : this.a)
          syncPair.dependency = tableDependency; 
      } 
    } 
  }
  
  private TableExistsDiff a() {
    for (AbstractDiff abstractDiff : this.b) {
      if (abstractDiff instanceof TableExistsDiff)
        return (TableExistsDiff)abstractDiff; 
    } 
    return null;
  }
  
  public void removeChildrenWithAction(SyncAction paramSyncAction) {
    for (SyncPair syncPair : this.a)
      syncPair.removeChildrenWithAction(paramSyncAction); 
    this.b.removeIf(paramAbstractDiff -> (paramAbstractDiff.getAction() == paramSyncAction));
    this.a.removeIf(paramSyncPair -> (paramSyncPair.a.isEmpty() && paramSyncPair.b.isEmpty()));
  }
  
  public boolean canSkipInDisplayAndShowChildrenDiffs() {
    return (this.a.isEmpty() && this.b.size() == 1);
  }
  
  public boolean matches(SyncDiffFilter paramSyncDiffFilter) {
    if (paramSyncDiffFilter.m() != null && ((
      this.left != null && this.left.getName().toLowerCase().contains(paramSyncDiffFilter.m().toLowerCase())) || (this.right != null && this.right
      .getName().toLowerCase().contains(paramSyncDiffFilter.m().toLowerCase()))))
      return true; 
    for (SyncPair syncPair : this.a) {
      if (syncPair.matches(paramSyncDiffFilter))
        return true; 
    } 
    for (AbstractDiff abstractDiff : this.b) {
      if (abstractDiff.matches(paramSyncDiffFilter))
        return true; 
    } 
    return false;
  }
  
  public void setAlwaysIncludeFks(boolean paramBoolean) {
    this.c = paramBoolean;
  }
}
