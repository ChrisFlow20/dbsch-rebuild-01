package com.wisecoders.dbs.data.filter;

import com.wisecoders.dbs.data.model.data.ObjectId;
import com.wisecoders.dbs.diagram.model.Attribute;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Filters {
  public static FilterPattern[] a = new FilterPattern[] { 
      (new FilterPattern(String.class, "equals", ":c = ':s'"))
      
      .a("Groovy", ":c == '$1'")
      .a("Any", ":c = '$1'")
      .a("MongoDb", "{:c:{\\$eq:'$1'}}"), (new FilterPattern(String.class, "different", ":c != ':s'"))
      
      .a("Groovy", ":c!='$1'")
      .a("Any", ":c!='$1'")
      .a("MongoDb", "{:c:{\\$ne:'$1'}}"), (new FilterPattern(String.class, "contains", ":c contains ':s'"))
      
      .a("Groovy", ":c =~ /.*$1.*/")
      .a("Any", ":c like '%$1%'")
      .a("MongoDb", "{:c:{\\$regex:'^.*$1.*\\$'}}"), (new FilterPattern(String.class, "starts with", ":c starts with ':s'"))
      
      .a("Groovy", ":c ==~ /$1.*/")
      .a("Any", ":c like '$1%'")
      .a("MongoDb", "{:c:{\\$regex:'^$1.*\\$'}}"), (new FilterPattern(String.class, "ends with", ":c ends with ':s'"))
      
      .a("Groovy", ":c ==~ /.*$1/ ")
      .a("Any", ":c like '%$1'")
      .a("MongoDb", "{:c:{\\$regex:'^.*$1\\$'}}"), (new FilterPattern(String.class, "is NULL", ":c is NULL"))
      
      .a("Groovy", ":c == null")
      .a("Any", ":c is NULL")
      .a("MongoDb", "{:c:{\\$exists:false }}"), (new FilterPattern(String.class, "is not null", ":c is not null"))
      
      .a("Groovy", ":c != null")
      .a("Any", ":c is not null")
      .a("MongoDb", "{:c:{\\$exists: true }}"), (new FilterPattern(String.class, "custom", ":s"))
      
      .a("Groovy", "$1")
      .a("Any", "$1")
      .a("MongoDb", "{:c:{$1}}")
      .a("Sample: trim( MyColumnName ) = 'Some' "), (new FilterPattern(ObjectId.class, "equals", ":c = ObjectId(':s')"))

      
      .a("Any", ":c = '$1'")
      .a("MongoDb", "{:c:{\\$eq:ObjectId('$1')}}"), (new FilterPattern(Number.class, "equals", ":c = :n"))

      
      .a("Groovy", ":c == $1")
      .a("Any", ":c = $1")
      .a("MongoDb", "{:c:{\\$eq:$1}}"), 
      (new FilterPattern(Number.class, "between", ":c between :n and :n"))
      
      .a("Groovy", ":c >= $1 && c: < $2")
      .a("Any", ":c between $1 and $2")
      .a("MongoDb", "{\\$and:[{:c:{\\$gte:$1 }},{:c:{\\$lte:$2}}]}"), (new FilterPattern(Number.class, "different", ":c != :n"))
      
      .a("Groovy", ":c!=$1")
      .a("Any", ":c!=$1")
      .a("MongoDb", "{:c:{\\$ne:$1}}"), (new FilterPattern(Number.class, "greater", ":c > :n"))
      
      .a("Groovy", ":c>$1")
      .a("Any", ":c>$1")
      .a("MongoDb", "{:c:{\\$gt:$1}}"), (new FilterPattern(Number.class, "greater equal", ":c >= :n"))
      
      .a("Groovy", ":c>=$1")
      .a("Any", ":c>=$1")
      .a("MongoDb", "{:c:{\\$gt:$1}}"), (new FilterPattern(Number.class, "smaller", ":c < :n"))
      
      .a("Groovy", ":c<$1")
      .a("Any", ":c<$1")
      .a("MongoDb", "{:c:{\\$lt:$1}}"), (new FilterPattern(Number.class, "smaller equal", ":c <= :n"))
      
      .a("Groovy", ":c<=$1")
      .a("Any", ":c<=$1")
      .a("MongoDb", "{:c:{\\$lte:$1}}"), (new FilterPattern(Number.class, "is NULL", ":c is NULL"))
      
      .a("Groovy", ":c == null")
      .a("Any", ":c is null")
      .a("MongoDb", "{:c:{\\$not:{\\$exists:true}}}"), (new FilterPattern(Number.class, "is not NULL", ":c is not null"))
      
      .a("Groovy", ":c != null")
      .a("Any", ":c is not null")
      .a("MongoDb", "{:c:{\\$exists:true}}"), (new FilterPattern(Number.class, "in (_,_...)", ":c in ( :s )"))
      
      .a("Any", ":c in \\($1\\)")
      .a("MongoDb", "{:c:{\\$in:{$1}}}").a("Comma separated list of numbers"), (new FilterPattern(Number.class, "custom", ":s"))
      
      .a("Groovy", "$1")
      .a("Any", "$1")
      .a("MongoDb", "'$1'")
      .a("Sample : abs( MyColumnName ) > 12, or for Mongo :  {'age':{ $gt: 18 }}"), 
      (new FilterPattern(Date.class, "after", ":c after ':d'"))

      
      .a("Groovy", ":c.after($1)")
      .a("Any", ":c>=$1")
      .a("MongoDb", "{:c:{\\$gt:new Date\\(\\)\\.parse\\('yyyy-MM-dd HH:mm:ss','$1'\\)}}"), (new FilterPattern(Date.class, "before", ":c before ':d'"))
      
      .a("Groovy", "$1.after(:c)")
      .a("Any", ":c<=$1")
      .a("MongoDb", "{:c:{\\$lt:new Date\\(\\)\\.parse\\('yyyy-MM-dd HH:mm:ss','$1'\\)}}"), (new FilterPattern(Date.class, "between", ":c between ':d' and ':d'"))
      
      .a("Groovy", "$1.after(:c) && :c.after($2)")
      .a("Any", ":c between $1 and $2")
      .a("MongoDb", "{\\$and:[{:c:{\\$gt:new Date\\(\\)\\.parse\\('yyyy-MM-dd HH:mm:ss','$1'\\)}},{:c:{\\$lt:new Date\\(\\)\\.parse\\('yyyy-MM-dd HH:mm:ss','$2'\\)}}]}"), (new FilterPattern(Date.class, "is NULL", ":c is NULL"))
      
      .a("Groovy", ":c == null")
      .a("Any", ":c is null")
      .a("MongoDb", "\"{:c:{\\\\\\$exists:false }}\""), (new FilterPattern(Date.class, "is not NULL", ":c is not NULL"))
      
      .a("Groovy", ":c != null")
      .a("Any", ":c is not null")
      .a("MongoDb", "\"{:c:{\\\\\\$not:{\\\\\\$exists: true }}}\""), (new FilterPattern(Date.class, "custom", ":s"))
      
      .a("Groovy", "$1")
      .a("Any", "$1")
      .a("MongoDb", "\"$1\"")
      .a("Sample : created='12-jan-2015'"), (new FilterPattern(Boolean.class, "is True", ":c equals true"))

      
      .a("Groovy", ":c == true")
      .a("Any", ":c = true")
      .a("SqlServer", ":c = 1")
      .a("MongoDb", "{:c:{\\$eq:true}}"), (new FilterPattern(Boolean.class, "is False", ":c equals false"))
      
      .a("Groovy", ":c == false")
      .a("Any", ":c = false")
      .a("SqlServer", ":c = 0")
      .a("MongoDb", "{:c:{\\$eq:false}}"), (new FilterPattern(Boolean.class, "is NULL", ":c is NULL"))
      
      .a("Groovy", ":c == null")
      .a("Any", ":c is null")
      .a("MongoDb", "{:c:{\\$exists:false }}"), (new FilterPattern(Boolean.class, "is not null", ":c is not null"))
      
      .a("Groovy", ":c != null")
      .a("Any", ":c is not null")
      .a("MongoDb", "{:c:{\\$exists: true }}") };
  
  public static List a(Class paramClass) {
    ArrayList<FilterPattern> arrayList = new ArrayList();
    for (FilterPattern filterPattern : a) {
      if (filterPattern.k == paramClass)
        arrayList.add(filterPattern); 
    } 
    return arrayList;
  }
  
  public static FilterPattern a(Attribute paramAttribute, String paramString) {
    Class<ObjectId> clazz;
    Class<String> clazz1 = String.class;
    if (paramAttribute.getDataType() != null)
      if (paramAttribute.getDataType().isNumeric()) {
        Class<Number> clazz2 = Number.class;
      } else if (paramAttribute.getDataType().isBoolean()) {
        Class<Boolean> clazz2 = Boolean.class;
      } else if (paramAttribute.getDataType().isDate()) {
        Class<Date> clazz2 = Date.class;
      } else if (paramAttribute.getDataType().isMongoDbObjectId()) {
        clazz = ObjectId.class;
      }  
    for (FilterPattern filterPattern : a) {
      if (filterPattern.k == clazz && filterPattern.b(paramString))
        return filterPattern; 
    } 
    return null;
  }
}
