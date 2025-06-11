package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Diagram;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.Folder;
import com.wisecoders.dbs.diagram.model.Relation;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.generator.engine.generators.BooleanGenerator;
import com.wisecoders.dbs.generator.engine.generators.DateGenerator;
import com.wisecoders.dbs.generator.engine.generators.ForeignKeyGenerator;
import com.wisecoders.dbs.generator.engine.generators.JsonListGenerator;
import com.wisecoders.dbs.generator.engine.generators.JsonMapGenerator;
import com.wisecoders.dbs.generator.engine.generators.SequenceGenerator;
import com.wisecoders.dbs.generator.engine.plan.DefinedPattern;
import com.wisecoders.dbs.generator.engine.plan.Domain;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.sys.fx.glyph.Glyph;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@DoNotObfuscate
public class Column extends AbstractUnit implements Attribute, DbUnit {
  private static final String w = "marker_pk.png";
  
  private static final String x = "marker_unq.png";
  
  private static final String y = "marker_idx.png";
  
  private static final String z = "marker_idx_nn.png";
  
  public static final String ICON_COLUMN = "marker_column.png";
  
  private static final String A = "marker_column_nn.png";
  
  private static final int B = 10;
  
  public final AbstractTable table;
  
  private boolean C;
  
  private int D;
  
  private int E = DataType.UNSET;
  
  private int F = DataType.UNSET;
  
  private String G;
  
  private AttributeSpec H = AttributeSpec.normal;
  
  private int I = -1;
  
  private String J;
  
  private short K = -1;
  
  private String L;
  
  private String M;
  
  private String N;
  
  private String O;
  
  private String P;
  
  private boolean Q = false;
  
  private DataType R;
  
  private short S = -1;
  
  private Sequence T;
  
  public String generatorPatternCache;
  
  public Column(AbstractTable paramAbstractTable, String paramString) {
    this(paramAbstractTable, paramString, DbmsTypes.get((paramAbstractTable.getSchema()).project.getDbId()).getDataType(4));
  }
  
  public Column(AbstractTable paramAbstractTable, String paramString, DataType paramDataType, AttributeSpec paramAttributeSpec) {
    this(paramAbstractTable, paramString, paramDataType);
    setSpec(paramAttributeSpec);
  }
  
  public Column(ChildEntity paramChildEntity, String paramString, DataType paramDataType) {
    this(paramChildEntity.ownerColumn.table, paramString, paramDataType);
    setUnitProperty(UnitProperty.c, paramChildEntity);
  }
  
  public Column(AbstractTable paramAbstractTable, String paramString, DataType paramDataType) {
    super(paramString);
    this.table = paramAbstractTable;
    if (paramDataType == null)
      throw new NullPointerException(); 
    this.R = paramDataType;
  }
  
  public String getDisplayName(Diagram paramDiagram) {
    String str = super.getDisplayName(paramDiagram);
    return (str.length() > Sys.B.displayNamesMaxLength.a()) ? (str.substring(0, Sys.B.displayNamesMaxLength.a()) + "..:") : str;
  }
  
  public Folder[] getSyncFolders() {
    (new Folder[1])[0] = (getChildEntity()).columns;
    return hasChildEntity() ? new Folder[1] : null;
  }
  
  public String getSymbolicName() {
    return is(UnitProperty.f).booleanValue() ? "Field" : (is(UnitProperty.g).booleanValue() ? "Attribute" : "Column");
  }
  
  public String getSymbolicIcon() {
    if (hasMarker(1))
      return "marker_pk.png"; 
    if (hasMarker(8))
      return "marker_unq.png"; 
    if (hasMarker(4))
      return isMandatory() ? "marker_idx_nn.png" : "marker_idx.png"; 
    if (getDataType().getJavaType() == 4999544)
      return "field_json.png"; 
    if (getDataType().getJavaType() == 4999545)
      return "field_list.png"; 
    return isMandatory() ? "marker_column_nn.png" : "marker_column.png";
  }
  
  public Glyph getSymbolicGlyph() {
    if (getDataType().getJavaType() == 4999544)
      return BootstrapIcons.diagram_2_fill; 
    if (getDataType().getJavaType() == 4999545)
      return BootstrapIcons.list; 
    return BootstrapIcons.view_list;
  }
  
  public String getIndexingIcon() {
    if (hasMarker(1))
      return "marker_pk.png"; 
    if (hasMarker(8))
      return "marker_unq.png"; 
    if (hasMarker(4))
      return "marker_idx.png"; 
    return null;
  }
  
  public void setDataType(DataType paramDataType) {
    if (paramDataType != null)
      this.R = paramDataType; 
  }
  
  public boolean hasLength() {
    return (this.E != DataType.UNSET);
  }
  
  public boolean hasDecimal() {
    return (this.F != DataType.UNSET);
  }
  
  public int getLength() {
    return this.E;
  }
  
  @GroovyMethod
  public void setLength(int paramInt) {
    this.E = paramInt;
  }
  
  @GroovyMethod
  public int getDecimal() {
    return this.F;
  }
  
  public void setDecimal(int paramInt) {
    if (paramInt < 0 && paramInt * -1 > this.E) {
      this.F = DataType.UNSET;
    } else {
      this.F = paramInt;
    } 
  }
  
  public DataType getDataType() {
    return this.R;
  }
  
  public boolean isUsingSameDataType(Column paramColumn) {
    if (!StringUtil.stringsAreEqualIgnoreCaseSpaces(getTypeString(DataTypeFormat.c), paramColumn.getTypeString(DataTypeFormat.c)))
      return false; 
    String str1 = (getDataType() instanceof UserDataType) ? ((UserDataType)getDataType()).getScript() : getTypeString(DataTypeFormat.c);
    String str2 = (paramColumn.getDataType() instanceof UserDataType) ? ((UserDataType)paramColumn.getDataType()).getScript() : paramColumn.getTypeString(DataTypeFormat.c);
    return StringUtil.stringsAreEqualIgnoreCaseSpaces(str1, str2);
  }
  
  public String getEnumeration() {
    return this.L;
  }
  
  public void setEnumeration(String paramString) {
    this.L = c(paramString);
  }
  
  public String getTypeOptions() {
    return (this.N != null) ? this.N : "";
  }
  
  public void setTypeOptions(String paramString) {
    this.N = c(paramString);
  }
  
  public String getOptions() {
    return (this.O != null) ? this.O : "";
  }
  
  public boolean setOptions(String paramString) {
    paramString = c(paramString);
    boolean bool = !StringUtil.areEqual(paramString, this.O) ? true : false;
    this.O = paramString;
    return bool;
  }
  
  public void appendOptions(String paramString) {
    if (this.O == null) {
      this.O = paramString;
    } else {
      this.O = this.O + " " + this.O;
    } 
  }
  
  public boolean isUnsigned() {
    return this.Q;
  }
  
  public void setUnsigned(boolean paramBoolean) {
    this.Q = paramBoolean;
  }
  
  public boolean isIdentity() {
    return (this.P != null);
  }
  
  public String getIdentity() {
    return this.P;
  }
  
  public void setIdentity(String paramString) {
    this.P = StringUtil.nullIfEmpty(paramString);
  }
  
  public void setDefaultValue(String paramString) {
    if (StringUtil.isEmpty(paramString)) {
      this.G = null;
    } else {
      this.G = StringUtil.removeNewLine(paramString, false);
    } 
  }
  
  public boolean hasDefaultValue() {
    return (this.G != null);
  }
  
  public String getDefaultValue() {
    return this.G;
  }
  
  public boolean defaultValuesAreSimilar(Column paramColumn) {
    if (paramColumn == null)
      return false; 
    return StringUtil.areEqualRemoveChars(getDefaultValue(), paramColumn.getDefaultValue(), "'");
  }
  
  public void cloneFrom(Column paramColumn) {
    setDataType(paramColumn.getDataType());
    setMandatory(paramColumn.isMandatory());
    setUnsigned(paramColumn.isUnsigned());
    setIdentity(paramColumn.getIdentity());
    setDefaultValue(paramColumn.getDefaultValue());
    setEnumeration(paramColumn.getEnumeration());
    setLength(paramColumn.getLength());
    setDecimal(paramColumn.getDecimal());
    setComment(paramColumn.getComment());
    setCommentTags(paramColumn.getCommentTags());
    setOptions(paramColumn.getOptions());
    setTypeOptions(paramColumn.getTypeOptions());
    setToDoFlag(paramColumn.getToDoFlag());
    setDefinition(paramColumn.getDefinition());
    setSpec(paramColumn.getSpec());
    setVirtual(paramColumn.isVirtual());
    Object object = paramColumn.getUnitProperty(UnitProperty.a);
    if (object != null)
      setUnitProperty(UnitProperty.a, object); 
    if (paramColumn.getAssociatedSequence() != null) {
      Schema schema = (getEntity().getSchema()).project.getSimilarSchema(paramColumn.getAssociatedSequence().getSchema());
      if (schema != null)
        setAssociatedSequence((Sequence)schema.sequences.getByName(paramColumn.getAssociatedSequence().getName())); 
    } 
    if (paramColumn.hasChildEntity())
      for (Column column1 : (paramColumn.getChildEntity()).columns) {
        Column column2 = getCreateChildEntity().createColumn(column1.getName(), column1.getDataType());
        column2.cloneFrom(column1);
      }  
  }
  
  public void setTicked(boolean paramBoolean) {}
  
  public boolean isTicked() {
    return false;
  }
  
  public AbstractTable getEntity() {
    return this.table;
  }
  
  public Entity getParentEntity() {
    Column column = getParentColumn();
    return (column != null && column.getChildEntity() != null) ? column.getChildEntity() : this.table;
  }
  
  public Column getRootParentColumn() {
    Column column = this;
    while (column.getParentColumn() != null)
      column = column.getParentColumn(); 
    return column;
  }
  
  public TreeUnit getParent() {
    Column column = getParentColumn();
    return (column != null) ? column : this.table.getAttributes();
  }
  
  public void setMarker(int paramInt) {
    this.D |= paramInt;
  }
  
  public boolean hasMarker(int paramInt) {
    return ((this.D & paramInt) > 0);
  }
  
  public void resetMarkers() {
    this.D = 0;
  }
  
  public void setMandatory(boolean paramBoolean) {
    this.C = paramBoolean;
  }
  
  public boolean isMandatory() {
    return this.C;
  }
  
  @GroovyMethod
  public String getTypeString() {
    return getDataType().getTypeString(DataTypeFormat.a, getLength(), getDecimal(), getEnumeration(), getTypeOptions());
  }
  
  @GroovyMethod
  public String getTypeString(DataTypeFormat paramDataTypeFormat) {
    return getDataType().getTypeString(paramDataTypeFormat, getLength(), getDecimal(), getEnumeration(), getTypeOptions());
  }
  
  public void refresh() {
    if (getDataType().isJsonList()) {
      setMarker(32768);
    } else if (getDataType().isJsonMap()) {
      setMarker(16384);
    } else if (getDataType().isNumeric()) {
      setMarker(128);
    } else if (getDataType().isArray()) {
      setMarker(8192);
    } else if (getDataType().isDate()) {
      setMarker(2048);
    } else if (getDataType().isChar()) {
      setMarker(1024);
    } else if (getDataType().isText()) {
      setMarker(512);
    } else if (getDataType().isBoolean()) {
      setMarker(256);
    } else if (getDataType().isBlobOrSimilar()) {
      setMarker(4096);
    } 
    if (this.T != null && this.T.isMarkedForDeletion())
      this.T = null; 
    if (hasChildEntity()) {
      if (isMarkedForDeletion())
        for (Column column : (getChildEntity()).columns)
          column.markForDeletion();  
      getChildEntity().refresh();
      DataType dataType1 = getDataType();
      if (!(getCreateChildEntity()).columns.isEmpty() && !dataType1.isJsonMapOrArray())
        setDataType(DbmsTypes.get((this.table.getSchema()).project.getDbId()).getOrCreateDataType(4999544, "map")); 
    } 
    DataType dataType = getDataType();
    if (dataType instanceof UserDataType) {
      UserDataType userDataType = (UserDataType)dataType;
      if (!userDataType.columns.isEmpty()) {
        Folder folder = (getCreateChildEntity()).columns;
        getChildEntity().rename(getName());
        for (Column column : folder) {
          if (getByName(column.getName()) == null)
            column.markForDeletion(); 
        } 
        for (Column column : userDataType.columns) {
          if (folder.getByName(column.getName()) == null)
            getCreateChildEntity().createColumn(column.getName(), column.getDataType()); 
        } 
      } 
    } 
  }
  
  public boolean isSelectable() {
    return false;
  }
  
  public int compareTo(AbstractUnit paramAbstractUnit) {
    if (paramAbstractUnit instanceof Column) {
      Column column = (Column)paramAbstractUnit;
      boolean bool1 = hasMarker(1), bool2 = column.hasMarker(1);
      if (bool1 || bool2) {
        if (bool1 == bool2) {
          boolean bool3 = hasMarker(16), bool4 = column.hasMarker(16);
          return (bool3 == bool4) ? 0 : (bool4 ? 1 : -1);
        } 
        return bool1 ? -1 : 1;
      } 
      return getName().compareTo(column.getName());
    } 
    return -1;
  }
  
  public void setToDoFlag(short paramShort) {
    this.S = paramShort;
  }
  
  public short getToDoFlag() {
    return this.S;
  }
  
  public String getGeneratorPattern() {
    return this.J;
  }
  
  public void setGeneratorPatternClearCache(String paramString) {
    this.J = paramString;
    this.generatorPatternCache = null;
  }
  
  public boolean setGeneratorPattern(String paramString) {
    if (!StringUtil.areEqual(paramString, this.J)) {
      this.J = paramString;
      this.generatorPatternCache = null;
      return true;
    } 
    return false;
  }
  
  public short getGeneratorNullsPercentage() {
    return isMandatory() ? 0 : ((this.K != -1) ? this.K : (short)Sys.B.generatorNullPercent.a());
  }
  
  public boolean hasGeneratorNullsPercentage() {
    return (!isMandatory() && this.K != -1);
  }
  
  public void setGeneratorNullsPercentage(short paramShort) {
    this.K = paramShort;
  }
  
  public boolean isUnique() {
    if (isIdentity())
      return true; 
    if (getEntity() instanceof Table)
      for (Index index : ((Table)getEntity()).getIndexes()) {
        if (index.isUnique() && index.getColumns().size() == 1 && index.getColumns().contains(this))
          return true; 
      }  
    return false;
  }
  
  public int getGeneratorSeed() {
    return this.I;
  }
  
  public void setGeneratorSeed(int paramInt) {
    this.I = paramInt;
  }
  
  public String getOrGuessGeneratorPattern() {
    if (StringUtil.isFilledTrim(this.J))
      return this.J; 
    if (this.generatorPatternCache != null)
      return this.generatorPatternCache; 
    if (this.H == AttributeSpec.computed || Dbms.get(getDbId()).isIdentityOrGenerated(this))
      return "skip"; 
    if (isIdentity() || "_id".equals(getName()))
      return "identity"; 
    if (this.T != null)
      return "db_sequence"; 
    if (hasMarker(32))
      return ForeignKeyGenerator.b; 
    String str = this.L;
    if (str == null && this.table instanceof Table && !((Table)this.table).constraints.isEmpty()) {
      Pattern pattern = Pattern.compile("\\s*" + Pattern.quote(getName()) + "\\s*IN\\s*\\(\\s*(.*)\\s*\\)\\s*", 2);
      for (Constraint constraint : ((Table)this.table).constraints) {
        if (constraint.getText() != null) {
          Matcher matcher = pattern.matcher(constraint.getText());
          if (matcher.matches())
            str = matcher.group(1); 
        } 
      } 
    } 
    if (StringUtil.isFilledTrim(str)) {
      StringBuilder stringBuilder = new StringBuilder();
      for (String str1 : StringUtil.parseLOV(str)) {
        if (!stringBuilder.isEmpty())
          stringBuilder.append("|"); 
        stringBuilder.append(str1);
      } 
      return stringBuilder.toString();
    } 
    if ((this.R.isNumeric() && this.E == 1) || this.R.getJavaType() == 16 || this.R.getJavaType() == -7)
      return BooleanGenerator.b; 
    if (this.R.isNumeric())
      return isUnique() ? SequenceGenerator.b : a(); 
    if (this.R.isUUID())
      return "uuid:"; 
    if (this.R.isTimestamp())
      return "timestamp:from='01.01.2008 00:00:00 000';to='01.01.2009 00:00:00 000';"; 
    if (this.R.isDate())
      return DateGenerator.b; 
    if (this.R.isJsonMap())
      return JsonMapGenerator.b; 
    if (this.R.isJsonList())
      return JsonListGenerator.b; 
    DefinedPattern definedPattern = Domain.a(this);
    if (definedPattern != null) {
      this.generatorPatternCache = definedPattern.e();
      return this.generatorPatternCache;
    } 
    return null;
  }
  
  private String a() {
    StringBuilder stringBuilder = new StringBuilder();
    switch (this.R.getJavaType()) {
      case 4:
        stringBuilder.append("int:from=0;to=2147483647;");
        break;
      case -5:
        stringBuilder.append("long:");
        break;
      case -6:
        stringBuilder.append("short:from=0;to=128;");
        break;
      case 5:
        stringBuilder.append("short:from=0;to=32768;");
        break;
      case 2:
        stringBuilder.append(hasDecimal() ? "double:" : "int:");
        break;
      case 6:
      case 7:
        stringBuilder.append("float:");
        break;
      default:
        stringBuilder.append("double:");
        break;
    } 
    byte b = hasLength() ? (getLength() - (hasDecimal() ? getDecimal() : 0)) : 0;
    if (!stringBuilder.toString().contains("to=") && hasLength()) {
      if (!b) {
        stringBuilder.append("from=0;to=1");
      } else {
        stringBuilder.append("from=0;to=");
        for (byte b1 = 0; b1 < b && b1 < 20; b1++)
          stringBuilder.append("9"); 
      } 
      stringBuilder.append(";");
    } 
    if (hasDecimal()) {
      StringBuilder stringBuilder1 = new StringBuilder();
      byte b1;
      for (b1 = 0; b1 < b && b1 < 20; b1++)
        stringBuilder1.append("#"); 
      stringBuilder1.append(stringBuilder1.isEmpty() ? "#########################." : ".");
      for (b1 = 0; b1 < getDecimal() && b1 < 8; b1++)
        stringBuilder1.append("#"); 
      stringBuilder.append("format=").append(stringBuilder1).append(";");
    } 
    return stringBuilder.toString();
  }
  
  public String toString() {
    return getNameWithPath();
  }
  
  public boolean hasAssociatedSequence() {
    return (this.T != null);
  }
  
  public Sequence getAssociatedSequence() {
    return this.T;
  }
  
  public void setAssociatedSequence(Sequence paramSequence) {
    this.T = paramSequence;
  }
  
  public String getNameWithPath() {
    Column column = getParentColumn();
    return (column != null) ? (column.getNameWithPath() + "." + column.getNameWithPath()) : getName();
  }
  
  public Column getParentColumn() {
    ChildEntity childEntity = (ChildEntity)getUnitProperty(UnitProperty.c);
    return (childEntity != null) ? childEntity.ownerColumn : null;
  }
  
  public boolean hasChildEntity() {
    return has(UnitProperty.b);
  }
  
  public ChildEntity getChildEntity() {
    return (ChildEntity)getUnitProperty(UnitProperty.b);
  }
  
  public ChildEntity getCreateChildEntity() {
    if (!has(UnitProperty.b))
      setUnitProperty(UnitProperty.b, new ChildEntity(this)); 
    return getChildEntity();
  }
  
  public int getChildrenCount() {
    return (!is(UnitProperty.g).booleanValue() && hasChildEntity()) ? (getCreateChildEntity()).columns.size() : 0;
  }
  
  public TreeUnit getChildAt(int paramInt) {
    return (!is(UnitProperty.g).booleanValue() && hasChildEntity()) ? (TreeUnit)(getCreateChildEntity()).columns.get(paramInt) : null;
  }
  
  public static Column getColumnByPath(Folder paramFolder, String paramString) {
    for (Column column : paramFolder) {
      if (paramString.equals(column.getNameWithPath()))
        return column; 
      if (paramString.startsWith(column.getNameWithPath() + ".")) {
        Column column1 = getColumnByPath((column.getCreateChildEntity()).columns, paramString);
        return (column1 != null) ? column1 : column;
      } 
    } 
    return null;
  }
  
  public AttributeSpec getSpec() {
    return this.H;
  }
  
  public void setSpec(AttributeSpec paramAttributeSpec) {
    this.H = paramAttributeSpec;
  }
  
  public void setDefinition(String paramString) {
    this.M = c(paramString);
  }
  
  public String getDefinition() {
    return this.M;
  }
  
  public String getDbId() {
    return this.table.getDbId();
  }
  
  public Schema getSchema() {
    return getEntity().getSchema();
  }
  
  public String ref() {
    if (getParentColumn() != null) {
      StringBuilder stringBuilder = new StringBuilder();
      Column column = this;
      boolean bool = false;
      while (column != null) {
        if (!stringBuilder.isEmpty()) {
          stringBuilder.insert(0, ".");
          bool = true;
        } 
        stringBuilder.insert(0, column.getName());
        column = column.getParentColumn();
      } 
      Dbms dbms = Dbms.get(getDbId());
      return (bool || dbms.isReservedKeyword(stringBuilder.toString())) ? dbms.quoteAlways(stringBuilder.toString()) : stringBuilder.toString();
    } 
    return (getSpec() == AttributeSpec.functional) ? getName() : Dbms.quote(this);
  }
  
  public boolean updateCascade(boolean paramBoolean, String paramString1, String paramString2, DataType paramDataType, int paramInt1, int paramInt2) {
    return a(paramBoolean, paramString1, paramString2, paramDataType, paramInt1, paramInt2, new ArrayList());
  }
  
  protected boolean a(boolean paramBoolean, String paramString1, String paramString2, DataType paramDataType, int paramInt1, int paramInt2, List<Column> paramList) {
    boolean bool = false;
    for (Relation relation : getEntity().getImportedRelations()) {
      int i = relation.getTargetAttributes().indexOf(this);
      if (i > -1) {
        Column column = (Column)relation.getSourceAttributes().get(i);
        if (column instanceof Column) {
          Column column1 = column;
          if (!paramList.contains(column1)) {
            paramList.add(column1);
            if (column1.getName().equals(paramString1)) {
              if (!paramBoolean)
                column1.rename(paramString2); 
              bool = true;
            } 
            if (column1.getDataType() != paramDataType) {
              if (!paramBoolean)
                column1.setDataType(paramDataType); 
              bool = true;
            } 
            if (column1.getLength() != paramInt1) {
              if (!paramBoolean)
                column1.setLength(paramInt1); 
              bool = true;
            } 
            if (column1.getDecimal() != paramInt2) {
              if (!paramBoolean)
                column1.setDecimal(paramInt2); 
              bool = true;
            } 
            if (column1.a(paramBoolean, paramString1, paramString2, paramDataType, paramInt1, paramInt2, paramList))
              bool = true; 
          } 
        } 
      } 
    } 
    return bool;
  }
}
