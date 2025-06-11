package com.wisecoders.dbs.project.governance.model;

import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Constraint;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.Sequence;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.StringUtil;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class InspectorRepository {
  public static final List a = new ArrayList();
  
  public static final List b = new ArrayList();
  
  public static void a(String paramString, Class paramClass, String[] paramArrayOfString, InspectorCallback paramInspectorCallback) {
    a.add(new FilterDefinition(paramString, paramClass, paramArrayOfString, paramInspectorCallback));
  }
  
  public static FilterDefinition a(String paramString) {
    for (FilterDefinition filterDefinition : a) {
      if (filterDefinition.a.equals(paramString))
        return filterDefinition; 
    } 
    return null;
  }
  
  public static void b(String paramString, Class paramClass, String[] paramArrayOfString, InspectorCallback paramInspectorCallback) {
    b.add(new FieldDefinition(paramString, paramClass, new String[] { "Pattern" }, paramInspectorCallback));
  }
  
  public static FieldDefinition b(String paramString) {
    for (FieldDefinition fieldDefinition : b) {
      if (fieldDefinition.a.equals(paramString))
        return fieldDefinition; 
    } 
    return null;
  }
  
  static {
    a("Name is Lowercase", AbstractUnit.class, null, (paramAbstractUnit, paramArrayOfString) -> Boolean.valueOf((paramAbstractUnit.getName() != null && paramAbstractUnit.getName().equals(paramAbstractUnit.getName().toLowerCase()))));
    a("Name is Uppercase", AbstractUnit.class, null, (paramAbstractUnit, paramArrayOfString) -> Boolean.valueOf((paramAbstractUnit.getName() != null && paramAbstractUnit.getName().equals(paramAbstractUnit.getName().toUpperCase()))));
    a("Name Starts with", AbstractUnit.class, new String[] { "Text" }, (paramAbstractUnit, paramArrayOfString) -> Boolean.valueOf((paramAbstractUnit.getName() != null && paramAbstractUnit.getName().startsWith(paramArrayOfString[0]))));
    a("Name Matches RegExp", AbstractUnit.class, new String[] { "RegExp" }, (paramAbstractUnit, paramArrayOfString) -> Boolean.valueOf((paramAbstractUnit.getName() != null && Pattern.compile(paramArrayOfString[0]).matcher(paramAbstractUnit.getName()).matches())));
    a("Has Description", AbstractUnit.class, null, (paramAbstractUnit, paramArrayOfString) -> Boolean.valueOf(StringUtil.isFilled(paramAbstractUnit.getComment())));
    a("Is Mandatory(Not Null)", Column.class, null, (paramAbstractUnit, paramArrayOfString) -> Boolean.valueOf(((Column)paramAbstractUnit).isMandatory()));
    a("Has Data Type", Column.class, null, (paramAbstractUnit, paramArrayOfString) -> Boolean.valueOf(StringUtil.isFilled(paramAbstractUnit.getComment())));
    a("Has Primary Key", Table.class, null, (paramAbstractUnit, paramArrayOfString) -> Boolean.valueOf((((Table)paramAbstractUnit).getPrimaryKey() != null)));
    a("Has Tag with Name", Column.class, new String[] { "Tag Name" }, (paramAbstractUnit, paramArrayOfString) -> Boolean.valueOf((paramAbstractUnit.getCommentTag(paramArrayOfString[0]) != null)));
    a("Has No Tag with Name", Column.class, new String[] { "Tag Name" }, (paramAbstractUnit, paramArrayOfString) -> Boolean.valueOf((paramAbstractUnit.getCommentTag(paramArrayOfString[0]) != null)));
    a("Options matches RegExp", AbstractUnit.class, new String[] { "RegExp" }, (paramAbstractUnit, paramArrayOfString) -> (paramAbstractUnit instanceof Table) ? Boolean.valueOf(Pattern.compile(paramArrayOfString[0]).matcher(((Table)paramAbstractUnit).getOptions()).matches()) : ((paramAbstractUnit instanceof Column) ? Boolean.valueOf(Pattern.compile(paramArrayOfString[0]).matcher(((Column)paramAbstractUnit).getOptions()).matches()) : ((paramAbstractUnit instanceof Index) ? Boolean.valueOf(Pattern.compile(paramArrayOfString[0]).matcher(((Index)paramAbstractUnit).getOptions()).matches()) : ((paramAbstractUnit instanceof Sequence) ? Boolean.valueOf(Pattern.compile(paramArrayOfString[0]).matcher(((Sequence)paramAbstractUnit).getOptions()).matches()) : ((paramAbstractUnit instanceof ForeignKey) ? Boolean.valueOf(Pattern.compile(paramArrayOfString[0]).matcher(((ForeignKey)paramAbstractUnit).getOptions()).matches()) : ((paramAbstractUnit instanceof Constraint) ? Boolean.valueOf(Pattern.compile(paramArrayOfString[0]).matcher(((Constraint)paramAbstractUnit).getOptions()).matches()) : Boolean.valueOf(false)))))));
    a("Java Groovy Check", AbstractUnit.class, new String[] { "Script" }, (paramAbstractUnit, paramArrayOfString) -> {
          Binding binding = new Binding();
          binding.setProperty("unit", paramAbstractUnit);
          GroovyShell groovyShell = new GroovyShell(binding);
          Script script = groovyShell.parse(paramArrayOfString[0], binding);
          Object object = script.run();
          return Boolean.valueOf((object != null && ((object instanceof Boolean && ((Boolean)object).booleanValue()) || (object instanceof String && "true".equalsIgnoreCase((String)object)))));
        });
    b("Name", AbstractUnit.class, null, (paramAbstractUnit, paramArrayOfString) -> paramAbstractUnit.getName());
    b("Comment", AbstractUnit.class, null, (paramAbstractUnit, paramArrayOfString) -> paramAbstractUnit.getComment());
    b("Options", Table.class, null, (paramAbstractUnit, paramArrayOfString) -> ((Table)paramAbstractUnit).getOptions());
    b("PK Name", Table.class, null, (paramAbstractUnit, paramArrayOfString) -> {
          Index index = ((Table)paramAbstractUnit).getPrimaryKey();
          return (index != null) ? index.getName() : null;
        });
    b("PK Columns", Table.class, null, (paramAbstractUnit, paramArrayOfString) -> {
          Index index = ((Table)paramAbstractUnit).getPrimaryKey();
          return (index != null) ? Index.listAttributes(index.columns) : null;
        });
    b("Mandatory", Column.class, null, (paramAbstractUnit, paramArrayOfString) -> "" + ((Column)paramAbstractUnit).isMandatory());
    b("Data Type", Column.class, null, (paramAbstractUnit, paramArrayOfString) -> ((Column)paramAbstractUnit).getTypeString());
    b("Identity", Column.class, null, (paramAbstractUnit, paramArrayOfString) -> ((Column)paramAbstractUnit).getIdentity());
    b("Default Value", Column.class, null, (paramAbstractUnit, paramArrayOfString) -> ((Column)paramAbstractUnit).getDefaultValue());
    b("Columns", Index.class, null, (paramAbstractUnit, paramArrayOfString) -> Index.listAttributes(((Index)paramAbstractUnit).columns));
    b("Options", Index.class, null, (paramAbstractUnit, paramArrayOfString) -> ((Index)paramAbstractUnit).getOptions());
    b("Is Unique", Index.class, null, (paramAbstractUnit, paramArrayOfString) -> "" + ((Index)paramAbstractUnit).isUnique());
    b("Referring Columns", ForeignKey.class, null, (paramAbstractUnit, paramArrayOfString) -> Index.listAttributes(((ForeignKey)paramAbstractUnit).getSourceAttributes()));
    b("Referred Columns", ForeignKey.class, null, (paramAbstractUnit, paramArrayOfString) -> Index.listAttributes(((ForeignKey)paramAbstractUnit).getTargetAttributes()));
    b("Options", ForeignKey.class, null, (paramAbstractUnit, paramArrayOfString) -> ((ForeignKey)paramAbstractUnit).getOptions());
    b("Delete Action", ForeignKey.class, null, (paramAbstractUnit, paramArrayOfString) -> String.valueOf(((ForeignKey)paramAbstractUnit).getDeleteAction()));
    b("Update Action", ForeignKey.class, null, (paramAbstractUnit, paramArrayOfString) -> String.valueOf(((ForeignKey)paramAbstractUnit).getUpdateAction()));
    b("Text", Constraint.class, null, (paramAbstractUnit, paramArrayOfString) -> ((Constraint)paramAbstractUnit).getText());
  }
}
