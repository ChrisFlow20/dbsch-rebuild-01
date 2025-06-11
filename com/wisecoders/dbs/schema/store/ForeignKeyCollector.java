package com.wisecoders.dbs.schema.store;

import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.schema.PxKey;
import com.wisecoders.dbs.schema.RelationCardinality;
import com.wisecoders.dbs.schema.RelationType;
import java.util.ArrayList;
import java.util.Collection;

public class ForeignKeyCollector {
  public static final String a = "";
  
  private final Collection b = new ArrayList();
  
  public ForeignKeyCollector$InternalFk a(String paramString, AbstractTable paramAbstractTable) {
    return a(paramString, paramAbstractTable, false, false, null, null, null, null, null);
  }
  
  public ForeignKeyCollector$InternalFk a(String paramString1, AbstractTable paramAbstractTable, boolean paramBoolean1, boolean paramBoolean2, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) {
    ForeignKeyCollector$InternalFk foreignKeyCollector$InternalFk = new ForeignKeyCollector$InternalFk((paramString1 == null) ? "" : paramString1, paramAbstractTable, paramBoolean1, paramBoolean2, paramString2, paramString3, RelationType.b, RelationCardinality.c, true, 0, 0, paramString4, paramString5, paramString6);
    this.b.add(foreignKeyCollector$InternalFk);
    return foreignKeyCollector$InternalFk;
  }
  
  public ForeignKeyCollector$InternalFk a(ProjectLoader paramProjectLoader, String paramString, AbstractTable paramAbstractTable) {
    String str1 = paramProjectLoader.get(PxKey.type);
    RelationType relationType = RelationType.b;
    if (str1 != null)
      try {
        relationType = RelationType.valueOf(str1);
      } catch (IllegalArgumentException illegalArgumentException) {} 
    String str2 = paramProjectLoader.get(PxKey.cardinality);
    RelationCardinality relationCardinality = RelationCardinality.c;
    if (str2 != null)
      try {
        relationCardinality = RelationCardinality.valueOf(str2);
      } catch (IllegalArgumentException illegalArgumentException) {} 
    ForeignKeyCollector$InternalFk foreignKeyCollector$InternalFk = new ForeignKeyCollector$InternalFk(paramString, paramAbstractTable, paramProjectLoader.getBoolean(PxKey.virtual), paramProjectLoader.getBoolean(PxKey.generated), paramProjectLoader.get(PxKey.to_schema), paramProjectLoader.get(PxKey.to_table), relationType, relationCardinality, paramProjectLoader.getBoolean(PxKey.mandatory), paramProjectLoader.getInt(PxKey.range_from), paramProjectLoader.getInt(PxKey.range_to), paramProjectLoader.get(PxKey.delete_action), paramProjectLoader.get(PxKey.update_action), paramProjectLoader.get(PxKey.options));
    this.b.add(foreignKeyCollector$InternalFk);
    return foreignKeyCollector$InternalFk;
  }
  
  public void a() {
    for (ForeignKeyCollector$InternalFk foreignKeyCollector$InternalFk : this.b)
      foreignKeyCollector$InternalFk.a(); 
    this.b.clear();
  }
  
  public ForeignKeyCollector$InternalFk a(AbstractTable paramAbstractTable, String paramString) {
    for (ForeignKeyCollector$InternalFk foreignKeyCollector$InternalFk : this.b) {
      if (foreignKeyCollector$InternalFk.getName().equals(paramString) && foreignKeyCollector$InternalFk.getEntity() == paramAbstractTable)
        return foreignKeyCollector$InternalFk; 
    } 
    return null;
  }
}
