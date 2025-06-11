package com.wisecoders.dbs.diagram.model;

import com.wisecoders.dbs.schema.RelationCardinality;
import com.wisecoders.dbs.sys.UniqueArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractDiagram {
  private static final double b = 1.2D;
  
  private static final double c = 1.09D;
  
  public static int cell = 16;
  
  protected PainterStatus a = PainterStatus.a;
  
  public int dim_X;
  
  public int dim_Y;
  
  public long[][] links;
  
  public final ConcurrentHashMap relationPositions = new ConcurrentHashMap<>();
  
  public static final int ITERATE_COST = 62;
  
  public static final long BIT_ENTITY = 65536L;
  
  public static final long BIT_CONNECTOR = 131072L;
  
  public static final long TYPE_STRAIGHT = 1048576L;
  
  public static final long TYPE_CROSS = 2097152L;
  
  public static final long TYPE_MERGE = 3145728L;
  
  public static final long TYPE_TERMINATOR_DEST = 8388608L;
  
  public static final long TYPE_TERMINATOR_SRC = 9437184L;
  
  public static final long IS_TERMINATOR = 8388608L;
  
  public static final long MAP_TYPE = 15728640L;
  
  public static final long RESET_TYPE = 68703748095L;
  
  public static final long MAP_CARDINALITY = 117440512L;
  
  public static final long MANY_TO_MANY = 134217728L;
  
  public static final long SOURCE_MANDATORY = 268435456L;
  
  public static final long TARGET_MANDATORY = 536870912L;
  
  public static final long CHILD_RELATION = 1073741824L;
  
  public static final long MAP_MANDATORY_UNIQUENESS = 1056964608L;
  
  public static final long HGHL = 4294967296L;
  
  public static final long SEL = 8589934592L;
  
  public static final long RESET_HGHL = -4294967297L;
  
  public static final long RESET_SEL = -8589934593L;
  
  public static final long VIRTUAL = 68719476736L;
  
  public static final long LINE = 137438953472L;
  
  public static final long DEDUCED = 274877906944L;
  
  public static final long MAP_COLOR = -1099511627776L;
  
  public static final int SHIFT_COLOR = 40;
  
  public static final int DIR_UP = 0;
  
  public static final int DIR_RIGHT = 1;
  
  public static final int DIR_DOWN = 2;
  
  public static final int DIR_LEFT = 3;
  
  public static final int SHIFT_TMPDIR = 8;
  
  public static final int SHIFT_DIR_FROM = 10;
  
  public static final int SHIFT_DIR_TO = 12;
  
  public static final long MAP_DIR = 3L;
  
  public static final long REFRESH = -132096L;
  
  public static final long REFRESH_TMPDIR = -1024L;
  
  public static void setCellSizeFromFontSize(double paramDouble) {
    cell = (int)(1.2D * Math.max(9.0D, paramDouble) + 1.09D);
  }
  
  public static int COST_START = 1;
  
  public static int COST_END = 255;
  
  public static int MAP_COST = 255;
  
  public static final int[] INCREMENTX = new int[] { 0, 1, 0, -1 };
  
  public static final int[] INCREMENTY = new int[] { -1, 0, 1, 0 };
  
  public static final int[] DECREMENTX = new int[] { 0, -1, 0, 1 };
  
  public static final int[] DECREMENTY = new int[] { 1, 0, -1, 0 };
  
  public static final int[] REVERSE_DIR = new int[] { 2, 3, 0, 1 };
  
  public static final int MAX_REL_SEARCH_CYCLE = 700;
  
  public int[][] lengthTable;
  
  public synchronized void setStatus(PainterStatus paramPainterStatus) {
    this.a = paramPainterStatus;
  }
  
  public PainterStatus getStatus() {
    return this.a;
  }
  
  private final List d = new UniqueArrayList();
  
  public int[][] debugFludded;
  
  public List findRelations(double paramDouble1, double paramDouble2, boolean paramBoolean) {
    this.d.clear();
    a((int)(paramDouble1 / cell), (int)(paramDouble2 / cell), -1);
    if (paramBoolean)
      this.d.add(a((int)paramDouble1, (int)paramDouble2)); 
    return this.d;
  }
  
  private Relation a(int paramInt1, int paramInt2) {
    if (this.a == PainterStatus.c || this.a == PainterStatus.d)
      for (Relation relation : this.relationPositions.keySet()) {
        if (((RelationPosition)this.relationPositions.get(relation)).a(paramInt1, paramInt2))
          return relation; 
      }  
    return null;
  }
  
  private void a(int paramInt1, int paramInt2, int paramInt3) {
    if (!this.a.a() || paramInt1 < 0 || paramInt2 < 0 || paramInt1 >= this.dim_X || paramInt2 >= this.dim_Y)
      return; 
    long l = this.links[paramInt1][paramInt2];
    if ((l & 0xF00000L) == 0L)
      return; 
    int i = (paramInt3 == -1) ? (int)(l >> 12L & 0x3L) : paramInt3;
    char c = 'ʼ';
    while ((l & 0xF00000L) != 9437184L && (paramInt1 += DECREMENTX[i]) >= 0 && paramInt1 < this.dim_X && (paramInt2 += DECREMENTY[i]) >= 0 && paramInt2 < this.dim_Y && --c > '\000') {
      if (!this.a.a())
        return; 
      l = this.links[paramInt1][paramInt2];
      if ((l & 0xF00000L) == 3145728L)
        for (Relation relation : this.relationPositions.keySet()) {
          RelationPosition relationPosition = (RelationPosition)this.relationPositions.get(relation);
          if (relationPosition != null && relationPosition.d == paramInt1 && relationPosition.e == paramInt2) {
            this.d.add(relation);
            a(relationPosition.d, relationPosition.e, relationPosition.f);
          } 
        }  
      i = ((l & 0xF00000L) == 2097152L) ? i : (int)(l >> 12L & 0x3L);
    } 
    for (Relation relation : this.relationPositions.keySet()) {
      RelationPosition relationPosition = (RelationPosition)this.relationPositions.get(relation);
      if (relationPosition != null && relationPosition.a == paramInt1 && relationPosition.b == paramInt2 && 
        !this.d.contains(relation))
        this.d.add(relation); 
    } 
  }
  
  public int highlightLine(Relation paramRelation, boolean paramBoolean1, boolean paramBoolean2) {
    RelationPosition relationPosition = (RelationPosition)this.relationPositions.get(paramRelation);
    if (relationPosition != null) {
      int i = relationPosition.a;
      int j = relationPosition.b;
      long l = this.links[i][j];
      int k = (int)(l >> 10L & 0x3L);
      if (paramBoolean1) {
        this.links[i][j] = this.links[i][j] | 0x200000000L;
      } else {
        this.links[i][j] = this.links[i][j] & 0xFFFFFFFDFFFFFFFFL;
      } 
      if (paramBoolean2) {
        this.links[i][j] = this.links[i][j] | 0x100000000L;
      } else {
        this.links[i][j] = this.links[i][j] & 0xFFFFFFFEFFFFFFFFL;
      } 
      byte b = 0;
      while ((i += INCREMENTX[k]) >= 0 && i < this.dim_X && (j += INCREMENTY[k]) >= 0 && j < this.dim_Y) {
        l = this.links[i][j];
        b++;
        if (b > '⟘')
          return b; 
        if ((l & 0xF00000L) != 3145728L || (l >> 12L & 0x3L) == k) {
          if (paramBoolean1) {
            this.links[i][j] = this.links[i][j] | 0x200000000L;
          } else {
            this.links[i][j] = this.links[i][j] & 0xFFFFFFFDFFFFFFFFL;
          } 
          if (paramBoolean2) {
            this.links[i][j] = this.links[i][j] | 0x100000000L;
          } else {
            this.links[i][j] = this.links[i][j] & 0xFFFFFFFEFFFFFFFFL;
          } 
        } 
        if ((l & 0xF00000L) == 8388608L)
          return b; 
        k = ((l & 0xF00000L) == 2097152L) ? k : (int)(l >> 10L & 0x3L);
      } 
    } 
    return 0;
  }
  
  public long getNeighbourPixel(int paramInt1, int paramInt2, int paramInt3) {
    int i = paramInt1 + INCREMENTX[paramInt3];
    int j = paramInt2 + INCREMENTY[paramInt3];
    long l = this.links[i][j];
    while ((l & 0xF00000L) == 2097152L) {
      i += INCREMENTX[paramInt3];
      j += INCREMENTY[paramInt3];
      l = this.links[i][j];
    } 
    if ((l & 0xF00000L) != 0L) {
      int k = (int)(l >> 10L & 0x3L);
      if (paramInt3 == REVERSE_DIR[k])
        return 0x100000L | l & 0xFFFFFF733F000000L; 
    } 
    return 0L;
  }
  
  public RelationCardinality getRelationCardinality(Relation paramRelation, boolean paramBoolean) {
    RelationPosition relationPosition = (RelationPosition)this.relationPositions.get(paramRelation);
    if (relationPosition == null)
      return RelationCardinality.a; 
    return paramBoolean ? getRelationCardinality(this.links[relationPosition.d][relationPosition.e]) : getRelationCardinality(this.links[relationPosition.a][relationPosition.b]);
  }
  
  public RelationCardinality getRelationCardinality(long paramLong) {
    if ((paramLong & 0xF00000L) == 8388608L) {
      if ((paramLong & 0x40000000L) > 0L)
        return RelationCardinality.f; 
      return RelationCardinality.a((int)(paramLong & 0x7000000L));
    } 
    if ((paramLong & 0x40000000L) > 0L)
      return RelationCardinality.b; 
    return RelationCardinality.a((int)(paramLong & 0x7000000L));
  }
  
  public boolean relationIsDrawn(Relation paramRelation) {
    return this.relationPositions.containsKey(paramRelation);
  }
  
  public boolean hasEndTerminator(Relation paramRelation) {
    RelationPosition relationPosition = (RelationPosition)this.relationPositions.get(paramRelation);
    return (relationPosition != null && (this.links[relationPosition.d][relationPosition.e] & 0xF00000L) == 8388608L);
  }
  
  public void vectorizeLine(Relation paramRelation, PathWriter paramPathWriter, boolean paramBoolean) {
    RelationPosition relationPosition = (RelationPosition)this.relationPositions.get(paramRelation);
    if (relationPosition != null) {
      int i = relationPosition.a, j = i, k = relationPosition.b, m = k, n = relationPosition.c;
      paramPathWriter.a(i * cell, k * cell);
      char c = 'ߐ';
      while ((i += INCREMENTX[n]) >= 0 && i < this.dim_X && (k += INCREMENTY[n]) >= 0 && k < this.dim_Y && Character.MIN_VALUE < c--) {
        long l = this.links[i][k];
        int i1 = ((l & 0xF00000L) == 2097152L) ? n : (int)(l >> 10L & 0x3L);
        if (i1 != n) {
          int i2 = i + INCREMENTX[i1], i3 = k + INCREMENTY[i1];
          j = (int)((cell * (j + i)) / 2.0D);
          m = (int)((cell * (m + k)) / 2.0D);
          i2 = (int)((cell * (i2 + i)) / 2.0D);
          i3 = (int)((cell * (i3 + k)) / 2.0D);
          paramPathWriter.b(j, m);
          paramPathWriter.a(i * cell, k * cell, i2, i3);
        } 
        if (i == relationPosition.d && k == relationPosition.e) {
          if ((this.links[i][k] & 0xF00000L) == 8388608L) {
            paramPathWriter.b(i * cell, k * cell);
            break;
          } 
          paramPathWriter.b((i + INCREMENTX[i1]) * cell, (k + INCREMENTY[i1]) * cell);
          break;
        } 
        n = i1;
        j = i;
        m = k;
      } 
      if (paramBoolean) {
        int[][] arrayOfInt = { { -1, 1, 1, 1 }, { -1, -1, -1, 1 }, { -1, -1, 1, -1 }, { 1, -1, 1, 1 } };
        paramPathWriter.b((i + arrayOfInt[n][0]) * cell, (k + arrayOfInt[n][1]) * cell);
        paramPathWriter.b(i * cell, k * cell);
        paramPathWriter.b((i + arrayOfInt[n][2]) * cell, (k + arrayOfInt[n][3]) * cell);
      } 
    } 
  }
  
  public String debugCell(int paramInt1, int paramInt2, boolean paramBoolean) {
    if (paramInt1 < 0 || paramInt2 < 0 || paramInt1 >= this.dim_X || paramInt2 >= this.dim_Y)
      return null; 
    long l1 = this.links[paramInt1][paramInt2];
    int i = (int)(l1 >> 10L & 0x3L);
    int j = (int)(l1 >> 12L & 0x3L);
    StringBuilder stringBuilder = new StringBuilder("<html>");
    Formatter formatter = new Formatter(stringBuilder);
    formatter.format("(%d,%d) ", new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2) });
    if ((l1 & 0xF00000L) > 0L)
      stringBuilder.append("LINE "); 
    long l2 = l1 & 0xF00000L;
    if (l2 == 1048576L)
      stringBuilder.append(" "); 
    if (l2 == 3145728L) {
      stringBuilder.append("JOIN ");
      for (byte b = 0; b < 4; b++) {
        if (b != i && b != REVERSE_DIR[j]) {
          long l = getNeighbourPixel(paramInt1, paramInt2, b);
          if (l > 0L)
            formatter.format(" (with %s, to %s ) ", new Object[] { a(b, paramBoolean), a(j, paramBoolean) }); 
        } 
      } 
    } else if (l2 == 2097152L) {
      stringBuilder.append("CROSS ");
    } else if (l2 == 8388608L) {
      stringBuilder.append("ARROW ");
    } else if (l2 == 9437184L) {
      stringBuilder.append("FOOT ");
    } 
    if ((l1 & 0x10000000L) > 0L)
      stringBuilder.append("*"); 
    if ((l1 & 0x7000000L) > 0L)
      stringBuilder.append("u"); 
    stringBuilder.append(a(i, paramBoolean));
    stringBuilder.append("-");
    if ((l1 & 0x20000000L) > 0L)
      stringBuilder.append("*"); 
    stringBuilder.append(a(j, paramBoolean));
    for (Relation relation : this.relationPositions.keySet()) {
      RelationPosition relationPosition = (RelationPosition)this.relationPositions.get(relation);
      if ((relationPosition.a == paramInt1 && relationPosition.b == paramInt2) || (relationPosition.d == paramInt1 && relationPosition.e == paramInt2))
        formatter.format(" %s (%d,%d,%s->%d,%d,%s) ", new Object[] { relation.getName(), Integer.valueOf(relationPosition.a), Integer.valueOf(relationPosition.b), 
              a(relationPosition.c, paramBoolean), Integer.valueOf(relationPosition.d), Integer.valueOf(relationPosition.e), a(relationPosition.f, paramBoolean) }); 
    } 
    if ((l1 & 0x10000L) > 0L)
      stringBuilder.append("BODY "); 
    if ((l1 & 0x20000L) > 0L)
      stringBuilder.append("CONNECTOR "); 
    if (this.debugFludded != null && this.debugFludded[paramInt1][paramInt2] > 0)
      stringBuilder.append("Fluded ").append(this.debugFludded[paramInt1][paramInt2]); 
    return stringBuilder.toString();
  }
  
  private String a(int paramInt, boolean paramBoolean) {
    switch (paramInt) {
      case 0:
        return 
          paramBoolean ? "&#8593;" : "^";
      case 1:
        return paramBoolean ? "&#8594;" : ">";
      case 2:
        return paramBoolean ? "&#8595;" : "V";
      case 3:
        return paramBoolean ? "&#8592;" : "<";
    } 
    return "";
  }
  
  public static int formatOnCell(double paramDouble) {
    return (int)(cell * Math.ceil(paramDouble / cell));
  }
  
  public static int formatOnEvenCell(double paramDouble) {
    int i = (int)Math.ceil(paramDouble / cell);
    if (i % 2 == 0)
      i++; 
    return i * cell;
  }
}
