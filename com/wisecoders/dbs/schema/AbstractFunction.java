package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractFunction extends Sql {
  private boolean a = false;
  
  public final List inputParameters = new ArrayList();
  
  public final List resultParameters = new ArrayList();
  
  public Schema schema;
  
  private static final Comparator b;
  
  public AbstractFunction(Schema paramSchema, String paramString) {
    super(paramString);
    this.schema = paramSchema;
  }
  
  public void setKnownParameters() {
    this.a = true;
  }
  
  public FunctionParameter addInputParameter(String paramString1, int paramInt1, String paramString2, int paramInt2, int paramInt3) {
    FunctionParameter functionParameter = new FunctionParameter(paramString1, paramInt1, paramString2, paramInt2, paramInt3);
    this.inputParameters.add(functionParameter);
    setKnownParameters();
    return functionParameter;
  }
  
  public FunctionParameter addResultParameter(String paramString1, int paramInt1, String paramString2, int paramInt2) {
    FunctionParameter functionParameter = new FunctionParameter(paramString1, paramInt1, paramString2, 5, paramInt2);
    this.resultParameters.add(functionParameter);
    setKnownParameters();
    return functionParameter;
  }
  
  public boolean isParametersKnown() {
    return this.a;
  }
  
  static {
    b = Comparator.comparingInt(paramFunctionParameter -> paramFunctionParameter.c);
  }
  
  public void orderParametersByPosition() {
    this.inputParameters.sort(b);
    this.resultParameters.sort(b);
  }
  
  public void cloneFrom(Sql paramSql) {
    super.cloneFrom(paramSql);
    if (paramSql instanceof AbstractFunction) {
      AbstractFunction abstractFunction = (AbstractFunction)paramSql;
      setUnitProperty(UnitProperty.d, abstractFunction.getString(UnitProperty.d));
      if (abstractFunction.isParametersKnown())
        setKnownParameters(); 
      for (FunctionParameter functionParameter : abstractFunction.inputParameters)
        addInputParameter(functionParameter.a, functionParameter.b, functionParameter.d, functionParameter.e, functionParameter.c); 
      for (FunctionParameter functionParameter : abstractFunction.resultParameters)
        addResultParameter(functionParameter.a, functionParameter.b, functionParameter.d, functionParameter.c); 
    } 
  }
  
  public String listInputParameterTypes() {
    StringBuilder stringBuilder = new StringBuilder();
    for (FunctionParameter functionParameter : this.inputParameters) {
      if (!stringBuilder.isEmpty())
        stringBuilder.append(", "); 
      stringBuilder.append(functionParameter.d);
    } 
    return stringBuilder.toString();
  }
  
  public String listInputParameterTypesInOut() {
    StringBuilder stringBuilder = new StringBuilder();
    for (FunctionParameter functionParameter : this.inputParameters) {
      if (!stringBuilder.isEmpty())
        stringBuilder.append(", "); 
      switch (functionParameter.e) {
        case 1:
          stringBuilder.append("IN ");
          break;
        case 3:
          stringBuilder.append("OUT ");
          break;
        case 2:
          stringBuilder.append("INOUT ");
          break;
      } 
      stringBuilder.append(functionParameter.d);
    } 
    return stringBuilder.toString();
  }
  
  public boolean sameAs(AbstractUnit paramAbstractUnit, boolean paramBoolean) {
    if (paramAbstractUnit instanceof AbstractFunction) {
      AbstractFunction abstractFunction = (AbstractFunction)paramAbstractUnit;
      if (super.sameAs(paramAbstractUnit, paramBoolean)) {
        if (isParametersKnown() && abstractFunction.isParametersKnown()) {
          if (this.inputParameters.size() != abstractFunction.inputParameters.size())
            return false; 
          for (Iterator<FunctionParameter> iterator1 = this.inputParameters.iterator(), iterator2 = abstractFunction.inputParameters.iterator(); iterator1.hasNext() && iterator2.hasNext(); ) {
            FunctionParameter functionParameter1 = iterator1.next(), functionParameter2 = iterator2.next();
            if (!functionParameter1.a(functionParameter2))
              return false; 
          } 
        } 
        return true;
      } 
    } 
    return false;
  }
  
  public String toString() {
    String str = listInputParameterTypes();
    return getName() + getName();
  }
}
