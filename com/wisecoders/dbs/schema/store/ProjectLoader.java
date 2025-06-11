package com.wisecoders.dbs.schema.store;

import com.wisecoders.dbs.browse.model.Browse;
import com.wisecoders.dbs.browse.model.BrowseTable;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Callout;
import com.wisecoders.dbs.diagram.model.CalloutPointer;
import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.Group;
import com.wisecoders.dbs.diagram.model.LineTextType;
import com.wisecoders.dbs.diagram.model.Point;
import com.wisecoders.dbs.diagram.model.Relation;
import com.wisecoders.dbs.diagram.model.Shape;
import com.wisecoders.dbs.diagram.model.ShapeStyle;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.project.store.AbstractContentHandler;
import com.wisecoders.dbs.query.model.engine.Aggregate;
import com.wisecoders.dbs.query.model.items.Query;
import com.wisecoders.dbs.query.model.items.QueryAggregate;
import com.wisecoders.dbs.query.model.items.QueryColumn;
import com.wisecoders.dbs.query.model.items.QueryFilter;
import com.wisecoders.dbs.query.model.items.QueryOrderBy;
import com.wisecoders.dbs.query.model.items.QueryTable;
import com.wisecoders.dbs.schema.AbstractFunction;
import com.wisecoders.dbs.schema.AttributeSpec;
import com.wisecoders.dbs.schema.ChildEntity;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Constraint;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.schema.Function;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.IndexType;
import com.wisecoders.dbs.schema.Language;
import com.wisecoders.dbs.schema.Layout;
import com.wisecoders.dbs.schema.MaterializedView;
import com.wisecoders.dbs.schema.Precision;
import com.wisecoders.dbs.schema.Procedure;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.ProjectType;
import com.wisecoders.dbs.schema.PxKey;
import com.wisecoders.dbs.schema.Rule;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Script;
import com.wisecoders.dbs.schema.Sequence;
import com.wisecoders.dbs.schema.Sql;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.schema.Table$TableType;
import com.wisecoders.dbs.schema.Trigger;
import com.wisecoders.dbs.schema.UserDataType;
import com.wisecoders.dbs.schema.View;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.FxUtil;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import javafx.scene.paint.Color;

@DoNotObfuscate
public class ProjectLoader extends AbstractContentHandler {
  private Project a;
  
  private final ForeignKeyCollector b = new ForeignKeyCollector();
  
  private static final int c = 70;
  
  public Project getProject() {
    FxUtil.a(() -> this.a.refresh());
    a();
    this.b.a();
    b();
    return this.a;
  }
  
  private final Unit[] d = new Unit[70];
  
  private final PxKey[] e = new PxKey[70];
  
  protected void a(String paramString, int paramInt) {
    PxKey pxKey = PxKey.valueOf(paramString);
    this.e[paramInt] = pxKey;
    this.d[paramInt] = a((paramInt > 0) ? this.d[paramInt - 1] : null, (paramInt > 0) ? this.e[paramInt - 1] : null, pxKey);
  }
  
  private Unit a(Unit paramUnit, PxKey paramPxKey1, PxKey paramPxKey2) {
    Project project;
    Schema schema1;
    View view1;
    Table table;
    MaterializedView materializedView1;
    View view2;
    MaterializedView materializedView2;
    UserDataType userDataType;
    Column column;
    Index index;
    Layout layout;
    Group group;
    Schema schema2;
    Callout callout1;
    Depict depict;
    Entity entity;
    Attribute attribute1;
    Shape shape;
    String str;
    BrowseTable browseTable;
    Callout callout2;
    Attribute attribute2;
    if (paramPxKey2 == PxKey.comment) {
      paramUnit.setComment(getBody());
      return paramUnit;
    } 
    if (paramPxKey2 == PxKey.comment_tag) {
      paramUnit.setCommentTag(get(PxKey.name), get(PxKey.value));
      return paramUnit;
    } 
    if (paramUnit == null) {
      String str1 = get(PxKey.database);
      if ("Azure".equals(str1))
        str1 = "SqlAzure"; 
      if (str1 == null)
        str1 = "MySql"; 
      this.a = new Project(get(PxKey.name), str1);
      this.a.setKey(get(PxKey.id));
      if (get(PxKey.type) != null)
        this.a.setType(ProjectType.valueOf(get(PxKey.type))); 
      return this.a;
    } 
    switch (ProjectLoader$1.a[paramPxKey1.ordinal()]) {
      case 35:
        project = (Project)paramUnit;
        switch (ProjectLoader$1.a[paramPxKey2.ordinal()]) {
          case 1:
            return a(project);
          case 2:
            ConnectorLoader.createConnector(this, project.getDbId());
          case 3:
            return b(project);
          case 4:
            this.a.setSyncFilter(getBody());
            break;
          case 5:
            this.a.setSyncInitScript(getBody());
            break;
          case 6:
            this.a.setDDLPreScript(getBody());
            break;
          case 7:
            this.a.setDDLPostScript(getBody());
            break;
        } 
        break;
      case 1:
        schema1 = (Schema)paramUnit;
        switch (ProjectLoader$1.a[paramPxKey2.ordinal()]) {
          case 8:
            return g(schema1);
          case 9:
            view1 = schema1.createView(get(PxKey.name));
            view1.setSyncPriority(getInt(PxKey.sync_priority));
            view1.setVirtual(getBoolean(PxKey.virtual));
            return view1;
          case 10:
            materializedView1 = schema1.createMaterializedView(get(PxKey.name));
            materializedView1.setSyncPriority(getInt(PxKey.sync_priority));
            materializedView1.setVirtual(getBoolean(PxKey.virtual));
            return materializedView1;
          case 11:
            return e(schema1);
          case 12:
            return c(schema1);
          case 13:
            return d(schema1);
          case 14:
            return b(schema1);
          case 15:
            return a(schema1);
          case 16:
            return f(schema1);
          case 17:
            schema1.setPreScript(getBody());
            break;
          case 18:
            schema1.setPostScript(getBody());
            break;
        } 
        break;
      case 8:
        table = (Table)paramUnit;
        switch (ProjectLoader$1.a[paramPxKey2.ordinal()]) {
          case 19:
            return c(table);
          case 20:
            return b(table);
          case 21:
            return a(table);
          case 22:
            return this.b.a(this, get(PxKey.name), (AbstractTable)paramUnit);
          case 23:
            table.setOptions(getBody());
            break;
          case 17:
            table.setPreScript(getBody());
            break;
          case 18:
            table.setPostScript(getBody());
            break;
        } 
        break;
      case 9:
        view2 = (View)paramUnit;
        if (paramPxKey2 == PxKey.view_script)
          view2.setScript(getBody()); 
        if (paramPxKey2 == PxKey.fk)
          return this.b.a(this, get(PxKey.name), (AbstractTable)paramUnit); 
        if (paramPxKey2 == PxKey.column) {
          Column column1 = view2.createColumn(get(PxKey.name), DbmsTypes.get(this.a.getDbId()).getOrCreateDataType(getInt(PxKey.jt), get(PxKey.type)));
          column1.setToDoFlag((short)getInt(PxKey.todo));
          return column1;
        } 
        break;
      case 10:
        materializedView2 = (MaterializedView)paramUnit;
        if (paramPxKey2 == PxKey.view_script)
          materializedView2.setScript(getBody()); 
        if (paramPxKey2 == PxKey.fk)
          return this.b.a(this, get(PxKey.name), (AbstractTable)paramUnit); 
        if (paramPxKey2 == PxKey.column)
          return materializedView2.createColumn(get(PxKey.name), DbmsTypes.get(this.a.getDbId()).getOrCreateDataType(getInt(PxKey.jt), get(PxKey.type))); 
        break;
      case 16:
        userDataType = (UserDataType)paramUnit;
        if (paramPxKey2 == PxKey.udt_script)
          userDataType.setScript(getBody()); 
        if (paramPxKey2 == PxKey.column)
          return userDataType.createColumn(get(PxKey.name), DbmsTypes.get(this.a.getDbId()).getOrCreateDataType(getInt(PxKey.jt), get(PxKey.type))); 
        break;
      case 22:
        if (paramPxKey2 == PxKey.fk_column) {
          ForeignKeyCollector$InternalFk foreignKeyCollector$InternalFk = (ForeignKeyCollector$InternalFk)paramUnit;
          foreignKeyCollector$InternalFk.i.add(get(PxKey.name));
          foreignKeyCollector$InternalFk.j.add(get(PxKey.pk));
        } 
        break;
      case 19:
        column = (Column)paramUnit;
        if (paramPxKey2 == PxKey.defo) {
          column.setDefaultValue(getBody());
          break;
        } 
        if (paramPxKey2 == PxKey.enumeration) {
          column.setEnumeration(getBody());
          break;
        } 
        if (paramPxKey2 == PxKey.identity) {
          column.setIdentity(getBody());
          break;
        } 
        if (paramPxKey2 == PxKey.column_options) {
          column.setOptions(getBody());
          break;
        } 
        if (paramPxKey2 == PxKey.type_options) {
          column.setTypeOptions(getBody());
          break;
        } 
        if (paramPxKey2 == PxKey.column)
          return a(column); 
        break;
      case 20:
        index = (Index)paramUnit;
        if (paramPxKey2 == PxKey.column) {
          Column column1 = index.getEntity().getColumnByNameOrPath(get(PxKey.name));
          if (column1 != null) {
            index.addColumn(column1);
            index.setColumnOptions(column1, (get(PxKey.options) != null) ? get(PxKey.options) : get(PxKey.sort));
          } 
        } 
        break;
      case 21:
        if (paramPxKey2 == PxKey.string) {
          Constraint constraint = (Constraint)paramUnit;
          constraint.setText(getBody());
        } 
        break;
      case 13:
      case 15:
        if (paramPxKey2 == PxKey.string)
          ((Sql)paramUnit).setText(getBody()); 
        break;
      case 12:
      case 14:
        switch (ProjectLoader$1.a[paramPxKey2.ordinal()]) {
          case 24:
            ((Sql)paramUnit).setText(getBody());
            break;
          case 25:
            ((AbstractFunction)paramUnit).addInputParameter(get(PxKey.name), getInt(PxKey.jt), get(PxKey.type), getInt(PxKey.inOut), 0);
            break;
          case 26:
            ((AbstractFunction)paramUnit).addResultParameter(get(PxKey.name), getInt(PxKey.jt), get(PxKey.type), 0);
            break;
        } 
        break;
      case 3:
        layout = (Layout)paramUnit;
        switch (ProjectLoader$1.a[paramPxKey2.ordinal()]) {
          case 27:
            return g(layout);
          case 28:
            return e(layout);
          case 29:
            return f(layout);
          case 30:
            return d(layout);
          case 31:
            return c(layout);
          case 32:
            return b(layout);
          case 33:
            return a(layout);
          case 34:
            return paramUnit;
        } 
        break;
      case 30:
        group = (Group)paramUnit;
        switch (ProjectLoader$1.a[paramPxKey2.ordinal()]) {
          case 27:
            schema2 = this.a.getSchema(get(PxKey.schema));
            if (schema2 != null) {
              ChildEntity childEntity;
              AbstractTable abstractTable = schema2.getTableOrViewOrMatViewOrUDT(get(PxKey.name));
              if (abstractTable instanceof Table && get(PxKey.column) != null)
                childEntity = ((Table)abstractTable).getChildEntityByPath(get(PxKey.column)); 
              Depict depict1 = group.diagram.getDepictFor(childEntity);
              if (depict1 != null)
                group.attachDepict(depict1); 
            } 
            break;
          case 28:
            callout1 = group.getLayout().createCallout(group, new Point(getInt(PxKey.x), getInt(PxKey.y)));
            if (get(PxKey.pointer) != null)
              callout1.a(CalloutPointer.valueOf(get(PxKey.pointer))); 
            return callout1;
        } 
        break;
      case 27:
        depict = (Depict)paramUnit;
        switch (ProjectLoader$1.a[paramPxKey2.ordinal()]) {
          case 28:
            entity = depict.getEntity();
            str = get(PxKey.on);
            if (str != null) {
              for (Relation relation : depict.getEntity().getRelations()) {
                if (relation.getName().equals(str))
                  Relation relation1 = relation; 
              } 
              for (Attribute attribute : depict.getEntity().getAttributes()) {
                if (attribute.getName().equals(str))
                  attribute1 = attribute; 
              } 
            } 
            callout2 = depict.diagram.createCallout(attribute1, new Point(getInt(PxKey.x), getInt(PxKey.y)));
            if (get(PxKey.pointer) != null)
              callout2.a(CalloutPointer.valueOf(get(PxKey.pointer))); 
            return callout2;
          case 19:
            if (!depict.hasHiddenAttributes())
              depict.hideAllAttributes(); 
            attribute2 = (Attribute)AbstractUnit.getByName(depict.getEntity().getAttributes(), get(PxKey.name));
            if (attribute2 != null)
              depict.setAttributeVisible(attribute2, true); 
            return depict;
        } 
        break;
      case 29:
        shape = (Shape)paramUnit;
        if (paramPxKey2 == PxKey.line)
          this.g.add(new b(shape, get(PxKey.name), getInt(PxKey.shape))); 
        break;
      case 33:
        if (paramPxKey2 == PxKey.string) {
          ((Script)paramUnit).setText(getBody());
          break;
        } 
        if (paramPxKey2 == PxKey.file)
          try {
            ((Script)paramUnit).setFile(new File(new URI(getBody())));
          } catch (URISyntaxException uRISyntaxException) {
            Log.b(uRISyntaxException);
          }  
        break;
      case 32:
        if (paramPxKey2 == PxKey.query_table)
          return a((Query)paramUnit, (QueryTable)null); 
        break;
      case 31:
        if (paramPxKey2 == PxKey.browse_table)
          return a((Browse)paramUnit); 
        break;
      case 34:
      case 36:
      case 37:
      case 38:
      case 39:
      case 40:
      case 41:
        return paramUnit;
      case 42:
        browseTable = (BrowseTable)paramUnit;
        if (paramPxKey2 == PxKey.browse_table)
          return a(browseTable); 
        if (paramPxKey2 == PxKey.filter) {
          Attribute attribute = (Attribute)AbstractUnit.getByName(browseTable.c.getAttributes(), get(PxKey.on));
          browseTable.a(attribute, get(PxKey.string));
        } 
        if (paramPxKey2 == PxKey.orderby) {
          Attribute attribute = (Attribute)AbstractUnit.getByName(browseTable.c.getAttributes(), get(PxKey.on));
          browseTable.a(attribute, getBoolean(PxKey.asc));
        } 
        break;
      case 43:
        if (paramUnit instanceof QueryTable) {
          QueryTable queryTable = (QueryTable)paramUnit;
          if (paramPxKey2 == PxKey.query_table)
            return a(queryTable.a, queryTable); 
          if (paramPxKey2 == PxKey.column) {
            QueryColumn queryColumn;
            String str1 = get(PxKey.name);
            Attribute attribute = (Attribute)AbstractUnit.getByName(queryTable.b.getAttributes(), str1);
            if (attribute == null) {
              queryTable.refresh();
              attribute = (Attribute)AbstractUnit.getByName(queryTable.c, str1);
            } 
            QueryAggregate queryAggregate = null;
            if (get(PxKey.aggregate) != null) {
              queryAggregate = queryTable.a(attribute, Aggregate.b(get(PxKey.aggregate)));
            } else if (get(PxKey.orderby) != null) {
              QueryOrderBy queryOrderBy = queryTable.b(attribute, "asc".equals(get(PxKey.orderby)));
            } else if (get(PxKey.filter) != null) {
              QueryFilter queryFilter = queryTable.a(attribute, get(PxKey.filter));
            } else if (attribute != null) {
              queryColumn = queryTable.c(attribute);
              queryColumn.setTicked(true);
            } 
            if (queryColumn != null)
              queryColumn.a(get(PxKey.alias)); 
          } 
        } 
        break;
    } 
    return paramUnit;
  }
  
  private Script a(Layout paramLayout) {
    Script script = paramLayout.createScript(get(PxKey.name));
    script.setKey(get(PxKey.id));
    script.setConfirmed(getBoolean(PxKey.confirmed));
    if (get(PxKey.language) != null)
      script.setLanguage(Language.valueOf(get(PxKey.language))); 
    return script;
  }
  
  private Query b(Layout paramLayout) {
    Query query = paramLayout.createQuery(get(PxKey.name));
    query.a(get(PxKey.id));
    query.setConfirmed(getBoolean(PxKey.confirmed));
    query.a(getBoolean(PxKey.groupby));
    return query;
  }
  
  private Browse c(Layout paramLayout) {
    Browse browse = paramLayout.createBrowse(get(PxKey.name));
    browse.a(get(PxKey.id));
    browse.setConfirmed(getBoolean(PxKey.confirmed));
    browse.b(getBoolean(PxKey.confirm_updates, true));
    return browse;
  }
  
  private Unit d(Layout paramLayout) {
    Group group = paramLayout.diagram.createGroup(get(PxKey.name));
    if (get(PxKey.color) != null)
      group.setColor(Color.web("#" + get(PxKey.color))); 
    return group;
  }
  
  private Unit e(Layout paramLayout) {
    Callout callout = paramLayout.diagram.createCallout("", new Point(getInt(PxKey.x), getInt(PxKey.y)));
    if (get(PxKey.pointer) != null)
      callout.a(CalloutPointer.valueOf(get(PxKey.pointer))); 
    return callout;
  }
  
  private Unit f(Layout paramLayout) {
    Shape shape = paramLayout.diagram.createShape("", new Point(getInt(PxKey.x), getInt(PxKey.y)));
    shape.a(ShapeStyle.valueOf(get(PxKey.style)));
    shape.a(Color.web("#" + get(PxKey.color)));
    return shape;
  }
  
  private Unit a(Query paramQuery, QueryTable paramQueryTable) {
    Schema schema = paramQuery.a.project.getSchema(get(PxKey.schema));
    if (schema != null) {
      ChildEntity childEntity;
      AbstractTable abstractTable = schema.getTableOrViewOrMatViewOrUDT(get(PxKey.name));
      if (abstractTable instanceof Table && get(PxKey.column) != null)
        childEntity = ((Table)abstractTable).getChildEntityByPath(get(PxKey.column)); 
      if (childEntity != null) {
        QueryTable queryTable = null;
        if (paramQueryTable != null) {
          Relation relation = (Relation)AbstractUnit.getByName(childEntity.getRelations(), get(PxKey.fk));
          if (relation == null)
            relation = (Relation)AbstractUnit.getByName(paramQueryTable.b.getRelations(), get(PxKey.fk)); 
          if (relation != null)
            queryTable = paramQueryTable.a(relation, get(PxKey.type)); 
        } 
        if (queryTable == null)
          queryTable = paramQuery.a(childEntity); 
        queryTable.b(get(PxKey.alias));
        paramQuery.b.attach(queryTable, getInt(PxKey.x), getInt(PxKey.y));
        return queryTable;
      } 
    } 
    return paramQuery;
  }
  
  private Unit a(AbstractUnit paramAbstractUnit) {
    Schema schema = this.a.getSchema(get(PxKey.schema));
    if (schema != null) {
      ChildEntity childEntity;
      AbstractTable abstractTable = schema.getTableOrViewOrMatViewOrUDT(get(PxKey.entity));
      if (abstractTable instanceof Table && get(PxKey.column) != null)
        childEntity = ((Table)abstractTable).getChildEntityByPath(get(PxKey.column)); 
      if (childEntity != null) {
        BrowseTable browseTable = null;
        if (paramAbstractUnit instanceof BrowseTable) {
          BrowseTable browseTable1 = (BrowseTable)paramAbstractUnit;
          Relation relation = (Relation)AbstractUnit.getByName(childEntity.getRelations(), get(PxKey.fk));
          if (relation == null)
            relation = (Relation)AbstractUnit.getByName(browseTable1.c.getRelations(), get(PxKey.fk)); 
          if (relation != null)
            browseTable = browseTable1.a(relation); 
        } else if (paramAbstractUnit instanceof Browse) {
          browseTable = ((Browse)paramAbstractUnit).a(childEntity);
        } 
        if (browseTable != null) {
          browseTable.e.b(getBoolean(PxKey.record_view));
          browseTable.a(getInt(PxKey.x), getInt(PxKey.y));
          browseTable.b(getInt(PxKey.width), getInt(PxKey.height));
          browseTable.b(get(PxKey.alias));
          browseTable.a(get(PxKey.filter));
          return browseTable;
        } 
      } 
    } 
    return paramAbstractUnit;
  }
  
  private Constraint a(Table paramTable) {
    Constraint constraint = paramTable.createConstraint(get(PxKey.name));
    constraint.setType(get(PxKey.type));
    constraint.setOptions(get(PxKey.options));
    constraint.setVirtual(getBoolean(PxKey.virtual));
    return constraint;
  }
  
  private Index b(Table paramTable) {
    Index index = paramTable.createIndex(get(PxKey.name));
    String str1 = get(PxKey.unique);
    String str2 = paramTable.getDbId();
    if ("CUSTOM1".equals(str1)) {
      index.setType("Snowflake".equals(str2) ? IndexType.CLUSTER : (("MySql".equals(str2) || "SqlServer".equals(str2)) ? IndexType.INDEX1 : IndexType.PARTITION));
    } else if ("CUSTOM2".equals(str1)) {
      index.setType("GoogleBigQuery".equals(str2) ? IndexType.CLUSTER : ("MySql".equals(str2) ? IndexType.INDEX2 : IndexType.SORT));
    } else if ("CUSTOM3".equals(str1)) {
      index.setType(IndexType.CLUSTER);
    } else if ("CUSTOM4".equals(str1)) {
      index.setType(IndexType.INDEX1);
    } else if ("UNIQUE".equals(str1) || "UNIQUE_CONSTRAINT".equals(str1)) {
      index.setType(IndexType.UNIQUE_KEY);
    } else if ("DISTRIBUTION_KEY".equals(str1)) {
      index.setType(IndexType.CLUSTER);
    } else if ("SORT_KEY".equals(str1)) {
      index.setType(IndexType.SORT);
    } else {
      index.setType(IndexType.valueOf(str1));
    } 
    index.setVirtual(getBoolean(PxKey.virtual, false));
    index.setOptions(get(PxKey.options));
    index.setSpecificationOptions(get(PxKey.spec));
    return index;
  }
  
  private Column c(Table paramTable) {
    DataType dataType = (DataType)paramTable.schema.userDataTypes.getByName(get(PxKey.type));
    if (dataType == null)
      dataType = DbmsTypes.get(this.a.getDbId()).getOrCreateDataType(getInt(PxKey.jt), get(PxKey.type)); 
    Column column = paramTable.createColumn(get(PxKey.name), dataType);
    column.setUnitProperty(UnitProperty.a, get(PxKey.prior));
    if (getInt(PxKey.length) != -1)
      column.setLength(getInt(PxKey.length)); 
    if (getInt(PxKey.decimal) != -1)
      column.setDecimal(getInt(PxKey.decimal)); 
    if (StringUtil.isFilledTrim(get(PxKey.computed))) {
      column.setSpec(AttributeSpec.computed);
      column.setDefinition(get(PxKey.computed));
    } else if (getBoolean(PxKey.functional)) {
      column.setSpec(AttributeSpec.functional);
    } else {
      if (get(PxKey.spec) != null)
        column.setSpec(AttributeSpec.valueOf(get(PxKey.spec))); 
      column.setDefinition(get(PxKey.definition));
    } 
    column.setMandatory(getBoolean(PxKey.mandatory));
    column.setUnsigned(getBoolean(PxKey.unsigned));
    column.setVirtual(getBoolean(PxKey.virtual));
    if (getBoolean(PxKey.autoincrement))
      column.setIdentity((Dbms.get(this.a.getDbId())).columnIdentityOptions.c_()); 
    column.setToDoFlag((short)getInt(PxKey.todo));
    column.setGeneratorPatternClearCache(get(PxKey.regexp));
    column.setGeneratorSeed(getInt(PxKey.seed));
    if (get(PxKey.sequence) != null)
      this.f.add(new a(column, get(PxKey.sequence))); 
    if (b(PxKey.regexp_nulls) != -1)
      column.setGeneratorNullsPercentage(b(PxKey.regexp_nulls)); 
    return column;
  }
  
  private Column a(Column paramColumn) {
    Column column = paramColumn.getCreateChildEntity().createColumn(
        get(PxKey.name), 
        DbmsTypes.get(this.a.getDbId()).getOrCreateDataType(getInt(PxKey.jt), get(PxKey.type)));
    if (getInt(PxKey.length) != -1)
      column.setLength(getInt(PxKey.length)); 
    if (getInt(PxKey.decimal) != -1)
      column.setDecimal(getInt(PxKey.decimal)); 
    column.setMandatory(getBoolean(PxKey.mandatory));
    column.setUnsigned(getBoolean(PxKey.unsigned));
    if (getBoolean(PxKey.autoincrement))
      column.setIdentity((Dbms.get(this.a.getDbId())).columnIdentityOptions.c_()); 
    column.setToDoFlag((short)getInt(PxKey.todo));
    column.setGeneratorPatternClearCache(get(PxKey.regexp));
    column.setGeneratorSeed(getInt(PxKey.seed));
    column.setVirtual(getBoolean(PxKey.virtual));
    if (b(PxKey.regexp_nulls) != -1)
      column.setGeneratorNullsPercentage(b(PxKey.regexp_nulls)); 
    return column;
  }
  
  private Trigger a(Schema paramSchema) {
    String str = get(PxKey.table);
    Table table = (str != null) ? paramSchema.getTable(str) : null;
    Trigger trigger = paramSchema.createTrigger(get(PxKey.name), table);
    trigger.setKey(get(PxKey.id));
    trigger.setIsSystem(getBoolean(PxKey.isSystem));
    trigger.setSyncPriority(getInt(PxKey.sync_priority));
    trigger.setVirtual(getBoolean(PxKey.virtual));
    return trigger;
  }
  
  private Function b(Schema paramSchema) {
    Function function = paramSchema.createFunction(get(PxKey.name));
    function.setKey(get(PxKey.id));
    function.setIsSystem(getBoolean(PxKey.isSystem));
    function.setSyncPriority(getInt(PxKey.sync_priority));
    function.setVirtual(getBoolean(PxKey.virtual));
    return function;
  }
  
  private Procedure c(Schema paramSchema) {
    Procedure procedure = paramSchema.createProcedure(get(PxKey.name));
    procedure.setKey(get(PxKey.id));
    procedure.setIsSystem(getBoolean(PxKey.isSystem));
    procedure.setSyncPriority(getInt(PxKey.sync_priority));
    procedure.setVirtual(getBoolean(PxKey.virtual));
    return procedure;
  }
  
  private Rule d(Schema paramSchema) {
    String str = get(PxKey.table);
    Table table = (str != null) ? paramSchema.getTable(str) : null;
    Rule rule = paramSchema.createRule(get(PxKey.name), table);
    rule.setKey(get(PxKey.id));
    rule.setVirtual(getBoolean(PxKey.virtual));
    return rule;
  }
  
  private Sequence e(Schema paramSchema) {
    Sequence sequence = paramSchema.createSequence(get(PxKey.name));
    sequence.setOptions(get(PxKey.options));
    sequence.setVirtual(getBoolean(PxKey.virtual));
    return sequence;
  }
  
  private UserDataType f(Schema paramSchema) {
    UserDataType userDataType = paramSchema.createUserDataType(get(PxKey.name));
    userDataType.setPrecision(Precision.valueOf(get(PxKey.udt_precision)));
    userDataType.setJavaType(getInt(PxKey.udt_java_type));
    userDataType.setVirtual(getBoolean(PxKey.virtual));
    userDataType.setSyncPriority(getInt(PxKey.sync_priority));
    return userDataType;
  }
  
  private Table g(Schema paramSchema) {
    Table table = paramSchema.createTable(get(PxKey.name));
    table.setUnitProperty(UnitProperty.a, get(PxKey.prior));
    table.setGeneratorRowsCount(getInt(PxKey.generator_rows));
    table.setGeneratorOrder(getInt(PxKey.generator_order));
    table.setRowCount(a(PxKey.row_count));
    table.setOptions(get(PxKey.options));
    table.setSpecificationOptions(get(PxKey.spec));
    table.setVirtual(getBoolean(PxKey.virtual));
    table.setSyncPriority(getInt(PxKey.sync_priority));
    if (get(PxKey.type) != null)
      table.setType(Table$TableType.valueOf(get(PxKey.type))); 
    return table;
  }
  
  private Schema a(Project paramProject) {
    Schema schema = paramProject.createSchema(get(PxKey.name), get(PxKey.catalogname));
    schema.setOptions(get(PxKey.options));
    schema.setSpecificationOptions(get(PxKey.spec));
    schema.setSyncPriority(getInt(PxKey.sync_priority));
    schema.setVirtual(getBoolean(PxKey.virtual));
    return schema;
  }
  
  private Layout b(Project paramProject) {
    Layout layout = paramProject.getLayout(get(PxKey.name));
    if (layout == null)
      layout = paramProject.createLayout(get(PxKey.name)); 
    layout.setStoreId(get(PxKey.id));
    layout.setConfirmed(getBoolean(PxKey.confirmed));
    layout.diagram.setJoinedRouting(getBoolean(PxKey.joined_routing));
    layout.diagram.setShowDeducedFks(getBoolean(PxKey.deduced_fks));
    layout.diagram.setShowDataType(getBoolean(PxKey.show_column_type));
    layout.diagram.setShowSchemaName(getBoolean(PxKey.show_schema_name));
    layout.diagram.setShowPhysicalName(getBoolean(PxKey.show_physical_name));
    layout.diagram.setShowPhysicalDictionaryName(getBoolean(PxKey.show_physical_dictionary_name));
    layout.diagram.setShowPageBorders(getBoolean(PxKey.show_page_borders));
    layout.diagram.setHideRelations(get(PxKey.hide_fks));
    if (get(PxKey.show_relation) != null)
      layout.diagram.setLineTextType(LineTextType.valueOf(get(PxKey.show_relation))); 
    a();
    this.b.a();
    b();
    return layout;
  }
  
  private final Collection f = new ArrayList();
  
  private void a() {
    for (a a : this.f) {
      Sequence sequence = (Sequence)(a.a.getEntity().getSchema()).sequences.getByName(a.b);
      if (sequence == null) {
        Log.f("SAXProjectLoader col seq loader error: table " + a.b + " not found.");
        continue;
      } 
      a.a.setAssociatedSequence(sequence);
    } 
    this.f.clear();
  }
  
  private final Collection g = new ArrayList();
  
  private void b() {
    for (b b : this.g)
      b.a.a(b.a.b.shapes.get(b.b), b.c); 
    this.g.clear();
  }
  
  private Depict g(Layout paramLayout) {
    Schema schema = this.a.getSchema(get(PxKey.schema));
    if (schema != null) {
      ChildEntity childEntity;
      AbstractTable abstractTable = schema.getTableOrViewOrMatViewOrUDT(get(PxKey.name));
      if (abstractTable != null && get(PxKey.column) != null)
        childEntity = abstractTable.getChildEntityByPath(get(PxKey.column)); 
      if (childEntity != null) {
        Depict depict = paramLayout.diagram.attach(childEntity, getInt(PxKey.x), getInt(PxKey.y));
        if (get(PxKey.color) != null)
          depict.setColor(Color.web("#" + get(PxKey.color))); 
        if (getBoolean(PxKey.hide_all))
          depict.hideAllAttributes(); 
        return depict;
      } 
    } 
    return null;
  }
}
