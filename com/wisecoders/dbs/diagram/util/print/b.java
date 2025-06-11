package com.wisecoders.dbs.diagram.util.print;

class b {
  protected static final int a = 256;
  
  protected static final int b = 499;
  
  protected static final int c = 491;
  
  protected static final int d = 487;
  
  protected static final int e = 503;
  
  protected static final int f = 1509;
  
  protected static final int g = 255;
  
  protected static final int h = 4;
  
  protected static final int i = 100;
  
  protected static final int j = 16;
  
  protected static final int k = 65536;
  
  protected static final int l = 10;
  
  protected static final int m = 1024;
  
  protected static final int n = 10;
  
  protected static final int o = 64;
  
  protected static final int p = 65536;
  
  protected static final int q = 32;
  
  protected static final int r = 6;
  
  protected static final int s = 64;
  
  protected static final int t = 2048;
  
  protected static final int u = 30;
  
  protected static final int v = 10;
  
  protected static final int w = 1024;
  
  protected int x;
  
  protected static final int y = 8;
  
  protected static final int z = 256;
  
  protected static final int A = 18;
  
  protected static final int B = 262144;
  
  protected byte[] C;
  
  protected int D;
  
  protected int E;
  
  protected int[][] F;
  
  protected int[] G = new int[256];
  
  protected int[] H = new int[256];
  
  protected int[] I = new int[256];
  
  protected int[] J = new int[32];
  
  public b(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
    this.C = paramArrayOfbyte;
    this.D = paramInt1;
    this.E = paramInt2;
    this.F = new int[256][];
    for (byte b1 = 0; b1 < 'Ā'; b1++) {
      this.F[b1] = new int[4];
      int[] arrayOfInt = this.F[b1];
      arrayOfInt[2] = (b1 << 12) / 256;
      arrayOfInt[1] = (b1 << 12) / 256;
      arrayOfInt[0] = (b1 << 12) / 256;
      this.I[b1] = 256;
      this.H[b1] = 0;
    } 
  }
  
  public byte[] a() {
    byte[] arrayOfByte = new byte[768];
    int[] arrayOfInt = new int[256];
    byte b1;
    for (b1 = 0; b1 < 'Ā'; b1++)
      arrayOfInt[this.F[b1][3]] = b1; 
    b1 = 0;
    for (byte b2 = 0; b2 < 'Ā'; b2++) {
      int i = arrayOfInt[b2];
      arrayOfByte[b1++] = (byte)this.F[i][0];
      arrayOfByte[b1++] = (byte)this.F[i][1];
      arrayOfByte[b1++] = (byte)this.F[i][2];
    } 
    return arrayOfByte;
  }
  
  public void b() {
    int j = 0;
    byte b2 = 0;
    for (byte b1 = 0; b1 < 'Ā'; b1++) {
      int[] arrayOfInt1 = this.F[b1];
      int m = b1;
      int n = arrayOfInt1[1];
      int k;
      for (k = b1 + 1; k < 256; k++) {
        int[] arrayOfInt = this.F[k];
        if (arrayOfInt[1] < n) {
          m = k;
          n = arrayOfInt[1];
        } 
      } 
      int[] arrayOfInt2 = this.F[m];
      if (b1 != m) {
        k = arrayOfInt2[0];
        arrayOfInt2[0] = arrayOfInt1[0];
        arrayOfInt1[0] = k;
        k = arrayOfInt2[1];
        arrayOfInt2[1] = arrayOfInt1[1];
        arrayOfInt1[1] = k;
        k = arrayOfInt2[2];
        arrayOfInt2[2] = arrayOfInt1[2];
        arrayOfInt1[2] = k;
        k = arrayOfInt2[3];
        arrayOfInt2[3] = arrayOfInt1[3];
        arrayOfInt1[3] = k;
      } 
      if (n != j) {
        this.G[j] = b2 + b1 >> 1;
        for (k = j + 1; k < n; k++)
          this.G[k] = b1; 
        j = n;
        b2 = b1;
      } 
    } 
    this.G[j] = b2 + 255 >> 1;
    for (int i = j + 1; i < 256; i++)
      this.G[i] = 255; 
  }
  
  public void c() {
    char c;
    if (this.D < 1509)
      this.E = 1; 
    this.x = 30 + (this.E - 1) / 3;
    byte[] arrayOfByte = this.C;
    int i1 = 0;
    int i2 = this.D;
    int n = this.D / 3 * this.E;
    int m = n / 100;
    int k = 1024;
    int i = 2048;
    int j = i >> 6;
    if (j <= 1)
      j = 0; 
    byte b1;
    for (b1 = 0; b1 < j; b1++)
      this.J[b1] = k * (j * j - b1 * b1) * 256 / j * j; 
    if (this.D < 1509) {
      c = '\003';
    } else if (this.D % 499 != 0) {
      c = 'י';
    } else if (this.D % 491 != 0) {
      c = 'ׁ';
    } else if (this.D % 487 != 0) {
      c = 'ֵ';
    } else {
      c = 'ץ';
    } 
    b1 = 0;
    while (b1 < n) {
      int i4 = (arrayOfByte[i1 + 0] & 0xFF) << 4;
      int i5 = (arrayOfByte[i1 + 1] & 0xFF) << 4;
      int i6 = (arrayOfByte[i1 + 2] & 0xFF) << 4;
      int i3 = b(i4, i5, i6);
      b(k, i3, i4, i5, i6);
      if (j != 0)
        a(j, i3, i4, i5, i6); 
      i1 += c;
      if (i1 >= i2)
        i1 -= this.D; 
      b1++;
      if (m == 0)
        m = 1; 
      if (b1 % m == 0) {
        k -= k / this.x;
        i -= i / 30;
        j = i >> 6;
        if (j <= 1)
          j = 0; 
        for (i3 = 0; i3 < j; i3++)
          this.J[i3] = k * (j * j - i3 * i3) * 256 / j * j; 
      } 
    } 
  }
  
  public int a(int paramInt1, int paramInt2, int paramInt3) {
    int k = 1000;
    int m = -1;
    int i = this.G[paramInt2];
    int j = i - 1;
    while (i < 256 || j >= 0) {
      if (i < 256) {
        int[] arrayOfInt = this.F[i];
        int n = arrayOfInt[1] - paramInt2;
        if (n >= k) {
          i = 256;
        } else {
          i++;
          if (n < 0)
            n = -n; 
          int i1 = arrayOfInt[0] - paramInt1;
          if (i1 < 0)
            i1 = -i1; 
          n += i1;
          if (n < k) {
            i1 = arrayOfInt[2] - paramInt3;
            if (i1 < 0)
              i1 = -i1; 
            n += i1;
            if (n < k) {
              k = n;
              m = arrayOfInt[3];
            } 
          } 
        } 
      } 
      if (j >= 0) {
        int[] arrayOfInt = this.F[j];
        int n = paramInt2 - arrayOfInt[1];
        if (n >= k) {
          j = -1;
          continue;
        } 
        j--;
        if (n < 0)
          n = -n; 
        int i1 = arrayOfInt[0] - paramInt1;
        if (i1 < 0)
          i1 = -i1; 
        n += i1;
        if (n < k) {
          i1 = arrayOfInt[2] - paramInt3;
          if (i1 < 0)
            i1 = -i1; 
          n += i1;
          if (n < k) {
            k = n;
            m = arrayOfInt[3];
          } 
        } 
      } 
    } 
    return m;
  }
  
  public byte[] d() {
    c();
    e();
    b();
    return a();
  }
  
  public void e() {
    for (byte b1 = 0; b1 < 'Ā'; b1++) {
      this.F[b1][0] = this.F[b1][0] >> 4;
      this.F[b1][1] = this.F[b1][1] >> 4;
      this.F[b1][2] = this.F[b1][2] >> 4;
      this.F[b1][3] = b1;
    } 
  }
  
  protected void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    int k = paramInt2 - paramInt1;
    if (k < -1)
      k = -1; 
    int m = paramInt2 + paramInt1;
    if (m > 256)
      m = 256; 
    int i = paramInt2 + 1;
    int j = paramInt2 - 1;
    byte b1 = 1;
    while (i < m || j > k) {
      int n = this.J[b1++];
      if (i < m) {
        int[] arrayOfInt = this.F[i++];
        try {
          arrayOfInt[0] = arrayOfInt[0] - n * (arrayOfInt[0] - paramInt3) / 262144;
          arrayOfInt[1] = arrayOfInt[1] - n * (arrayOfInt[1] - paramInt4) / 262144;
          arrayOfInt[2] = arrayOfInt[2] - n * (arrayOfInt[2] - paramInt5) / 262144;
        } catch (Exception exception) {}
      } 
      if (j > k) {
        int[] arrayOfInt = this.F[j--];
        try {
          arrayOfInt[0] = arrayOfInt[0] - n * (arrayOfInt[0] - paramInt3) / 262144;
          arrayOfInt[1] = arrayOfInt[1] - n * (arrayOfInt[1] - paramInt4) / 262144;
          arrayOfInt[2] = arrayOfInt[2] - n * (arrayOfInt[2] - paramInt5) / 262144;
        } catch (Exception exception) {}
      } 
    } 
  }
  
  protected void b(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) {
    int[] arrayOfInt = this.F[paramInt2];
    arrayOfInt[0] = arrayOfInt[0] - paramInt1 * (arrayOfInt[0] - paramInt3) / 1024;
    arrayOfInt[1] = arrayOfInt[1] - paramInt1 * (arrayOfInt[1] - paramInt4) / 1024;
    arrayOfInt[2] = arrayOfInt[2] - paramInt1 * (arrayOfInt[2] - paramInt5) / 1024;
  }
  
  protected int b(int paramInt1, int paramInt2, int paramInt3) {
    int i = Integer.MAX_VALUE;
    int j = i;
    byte b2 = -1;
    byte b3 = b2;
    for (byte b1 = 0; b1 < 'Ā'; b1++) {
      int[] arrayOfInt = this.F[b1];
      int k = arrayOfInt[0] - paramInt1;
      if (k < 0)
        k = -k; 
      int m = arrayOfInt[1] - paramInt2;
      if (m < 0)
        m = -m; 
      k += m;
      m = arrayOfInt[2] - paramInt3;
      if (m < 0)
        m = -m; 
      k += m;
      if (k < i) {
        i = k;
        b2 = b1;
      } 
      int n = k - (this.H[b1] >> 12);
      if (n < j) {
        j = n;
        b3 = b1;
      } 
      int i1 = this.I[b1] >> 10;
      this.I[b1] = this.I[b1] - i1;
      this.H[b1] = this.H[b1] + (i1 << 10);
    } 
    this.I[b2] = this.I[b2] + 64;
    this.H[b2] = this.H[b2] - 65536;
    return b3;
  }
}
