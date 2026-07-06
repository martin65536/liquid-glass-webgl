package defpackage;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class yl0 extends y implements Collection, q30 {
    public b0 e;
    public Object[] f;
    public Object[] g;
    public int h;
    public rt i = new rt(12);
    public Object[] j;
    public Object[] k;
    public int l;

    public yl0(b0 b0Var, Object[] objArr, Object[] objArr2, int i) {
        this.e = b0Var;
        this.f = objArr;
        this.g = objArr2;
        this.h = i;
        this.j = objArr;
        this.k = objArr2;
        this.l = b0Var.a();
    }

    public static void d(Object[] objArr, int i, Iterator it) {
        while (i < 32 && it.hasNext()) {
            objArr[i] = it.next();
            i++;
        }
    }

    public final Object A(Object[] objArr, int i, int i2, int i3) {
        int i4 = this.l - i;
        Object[] objArr2 = this.k;
        if (i4 == 1) {
            Object obj = objArr2[0];
            q(objArr, i, i2);
            return obj;
        }
        Object obj2 = objArr2[i3];
        Object[] k = k(objArr2);
        i8.N(objArr2, k, i3, i3 + 1, i4);
        k[i4 - 1] = null;
        this.j = objArr;
        this.k = k;
        this.l = (i + i4) - 1;
        this.h = i2;
        return obj2;
    }

    public final int B() {
        int i = this.l;
        if (i <= 32) {
            return 0;
        }
        return (i - 1) & (-32);
    }

    public final Object[] C(Object[] objArr, int i, int i2, Object obj, j1 j1Var) {
        int v = t20.v(i2, i);
        Object[] k = k(objArr);
        if (i == 0) {
            if (k != objArr) {
                ((AbstractList) this).modCount++;
            }
            j1Var.a = k[v];
            k[v] = obj;
            return k;
        }
        Object obj2 = k[v];
        obj2.getClass();
        k[v] = C((Object[]) obj2, i - 5, i2, obj, j1Var);
        return k;
    }

    public final void D(Collection collection, int i, Object[] objArr, int i2, Object[][] objArr2, int i3, Object[] objArr3) {
        Object[] m;
        if (i3 < 1) {
            cn0.a("requires at least one nullBuffer");
        }
        Object[] k = k(objArr);
        objArr2[0] = k;
        int i4 = i & 31;
        int size = ((collection.size() + i) - 1) & 31;
        int i5 = (i2 - i4) + size;
        if (i5 < 32) {
            i8.N(k, objArr3, size + 1, i4, i2);
        } else {
            int i6 = i5 - 31;
            if (i3 == 1) {
                m = k;
            } else {
                m = m();
                i3--;
                objArr2[i3] = m;
            }
            int i7 = i2 - i6;
            i8.N(k, objArr3, 0, i7, i2);
            i8.N(k, m, size + 1, i4, i7);
            objArr3 = m;
        }
        Iterator it = collection.iterator();
        d(k, i4, it);
        for (int i8 = 1; i8 < i3; i8++) {
            Object[] m2 = m();
            d(m2, 0, it);
            objArr2[i8] = m2;
        }
        d(objArr3, 0, it);
    }

    public final int E() {
        int i = this.l;
        if (i <= 32) {
            return i;
        }
        return i - ((i - 1) & (-32));
    }

    @Override // defpackage.y
    public final int a() {
        return this.l;
    }

    @Override // java.util.AbstractList, java.util.List
    public final void add(int i, Object obj) {
        m20.l(i, a());
        if (i == a()) {
            add(obj);
            return;
        }
        ((AbstractList) this).modCount++;
        int B = B();
        if (i >= B) {
            h(this.j, i - B, obj);
            return;
        }
        j1 j1Var = new j1(null);
        Object[] objArr = this.j;
        objArr.getClass();
        h(g(objArr, this.h, i, obj, j1Var), 0, j1Var.a);
    }

    @Override // java.util.AbstractList, java.util.List
    public final boolean addAll(int i, Collection collection) {
        Collection collection2;
        Object[] m;
        m20.l(i, this.l);
        if (i == this.l) {
            return addAll(collection);
        }
        if (collection.isEmpty()) {
            return false;
        }
        ((AbstractList) this).modCount++;
        int i2 = (i >> 5) << 5;
        int size = ((collection.size() + (this.l - i2)) - 1) / 32;
        if (size == 0) {
            int i3 = i & 31;
            int size2 = ((collection.size() + i) - 1) & 31;
            Object[] objArr = this.k;
            Object[] k = k(objArr);
            i8.N(objArr, k, size2 + 1, i3, E());
            d(k, i3, collection.iterator());
            this.k = k;
            this.l = collection.size() + this.l;
            return true;
        }
        Object[][] objArr2 = new Object[size];
        int E = E();
        int size3 = collection.size() + this.l;
        if (size3 > 32) {
            size3 -= (size3 - 1) & (-32);
        }
        if (i >= B()) {
            m = m();
            collection2 = collection;
            D(collection2, i, this.k, E, objArr2, size, m);
            objArr2 = objArr2;
        } else {
            collection2 = collection;
            Object[] objArr3 = this.k;
            if (size3 > E) {
                int i4 = size3 - E;
                Object[] l = l(i4, objArr3);
                f(collection2, i, i4, objArr2, size, l);
                objArr2 = objArr2;
                m = l;
            } else {
                m = m();
                int i5 = E - size3;
                i8.N(objArr3, m, 0, i5, E);
                int i6 = 32 - i5;
                Object[] l2 = l(i6, this.k);
                int i7 = size - 1;
                objArr2[i7] = l2;
                f(collection2, i, i6, objArr2, i7, l2);
                collection2 = collection2;
            }
        }
        this.j = s(this.j, i2, objArr2);
        this.k = m;
        this.l = collection2.size() + this.l;
        return true;
    }

    @Override // defpackage.y
    public final Object b(int i) {
        m20.j(i, a());
        ((AbstractList) this).modCount++;
        int B = B();
        if (i >= B) {
            return A(this.j, B, this.h, i - B);
        }
        j1 j1Var = new j1(this.k[0]);
        Object[] objArr = this.j;
        objArr.getClass();
        A(z(objArr, this.h, i, j1Var), B, this.h, 0);
        return j1Var.a;
    }

    public final b0 c() {
        b0 xl0Var;
        Object[] objArr = this.j;
        if (objArr == this.f && this.k == this.g) {
            xl0Var = this.e;
        } else {
            this.i = new rt(12);
            this.f = objArr;
            Object[] objArr2 = this.k;
            this.g = objArr2;
            if (objArr == null) {
                if (objArr2.length == 0) {
                    xl0Var = vw0.f;
                } else {
                    xl0Var = new vw0(Arrays.copyOf(objArr2, this.l));
                }
            } else {
                xl0Var = new xl0(objArr, objArr2, this.l, this.h);
            }
        }
        this.e = xl0Var;
        return xl0Var;
    }

    public final int e() {
        return ((AbstractList) this).modCount;
    }

    public final void f(Collection collection, int i, int i2, Object[][] objArr, int i3, Object[] objArr2) {
        if (this.j != null) {
            int i4 = i >> 5;
            x j = j(B() >> 5);
            int i5 = i3;
            Object[] objArr3 = objArr2;
            while (j.e - 1 != i4) {
                Object[] objArr4 = (Object[]) j.previous();
                i8.N(objArr4, objArr3, 0, 32 - i2, 32);
                objArr3 = l(i2, objArr4);
                i5--;
                objArr[i5] = objArr3;
            }
            Object[] objArr5 = (Object[]) j.previous();
            int B = i3 - (((B() >> 5) - 1) - i4);
            if (B < i3) {
                objArr2 = objArr[B];
                objArr2.getClass();
            }
            D(collection, i, objArr5, 32, objArr, B, objArr2);
            return;
        }
        v7.o("root is null");
    }

    public final Object[] g(Object[] objArr, int i, int i2, Object obj, j1 j1Var) {
        Object obj2;
        int v = t20.v(i2, i);
        if (i == 0) {
            j1Var.a = objArr[31];
            Object[] k = k(objArr);
            i8.N(objArr, k, v + 1, v, 31);
            k[v] = obj;
            return k;
        }
        Object[] k2 = k(objArr);
        int i3 = i - 5;
        Object obj3 = k2[v];
        obj3.getClass();
        k2[v] = g((Object[]) obj3, i3, i2, obj, j1Var);
        while (true) {
            v++;
            if (v >= 32 || (obj2 = k2[v]) == null) {
                break;
            }
            k2[v] = g((Object[]) obj2, i3, 0, j1Var.a, j1Var);
        }
        return k2;
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object get(int i) {
        Object[] objArr;
        m20.j(i, a());
        if (B() <= i) {
            objArr = this.k;
        } else {
            Object[] objArr2 = this.j;
            objArr2.getClass();
            for (int i2 = this.h; i2 > 0; i2 -= 5) {
                Object[] objArr3 = objArr2[t20.v(i, i2)];
                objArr3.getClass();
                objArr2 = objArr3;
            }
            objArr = objArr2;
        }
        return objArr[i & 31];
    }

    public final void h(Object[] objArr, int i, Object obj) {
        int E = E();
        Object[] k = k(this.k);
        Object[] objArr2 = this.k;
        if (E < 32) {
            i8.N(objArr2, k, i + 1, i, E);
            k[i] = obj;
            this.j = objArr;
            this.k = k;
            this.l++;
            return;
        }
        Object obj2 = objArr2[31];
        i8.N(objArr2, k, i + 1, i, 31);
        k[i] = obj;
        t(objArr, k, n(obj2));
    }

    public final boolean i(Object[] objArr) {
        if (objArr.length == 33 && objArr[32] == this.i) {
            return true;
        }
        return false;
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.List
    public final Iterator iterator() {
        return listIterator(0);
    }

    public final x j(int i) {
        Object[] objArr = this.j;
        if (objArr != null) {
            int B = B() >> 5;
            m20.l(i, B);
            int i2 = this.h;
            if (i2 == 0) {
                return new wb(i, objArr);
            }
            return new z21(objArr, i, B, i2 / 5);
        }
        v7.o("Invalid root");
        return null;
    }

    public final Object[] k(Object[] objArr) {
        if (objArr == null) {
            return m();
        }
        if (i(objArr)) {
            return objArr;
        }
        Object[] m = m();
        int length = objArr.length;
        if (length > 32) {
            length = 32;
        }
        i8.P(objArr, m, 0, length, 6);
        return m;
    }

    public final Object[] l(int i, Object[] objArr) {
        if (i(objArr)) {
            i8.N(objArr, objArr, i, 0, 32 - i);
            return objArr;
        }
        Object[] m = m();
        i8.N(objArr, m, i, 0, 32 - i);
        return m;
    }

    @Override // java.util.AbstractList, java.util.List
    public final ListIterator listIterator(int i) {
        m20.l(i, this.l);
        return new am0(this, i);
    }

    public final Object[] m() {
        Object[] objArr = new Object[33];
        objArr[32] = this.i;
        return objArr;
    }

    public final Object[] n(Object obj) {
        Object[] objArr = new Object[33];
        objArr[0] = obj;
        objArr[32] = this.i;
        return objArr;
    }

    public final Object[] o(Object[] objArr, int i, int i2) {
        if (i2 < 0) {
            cn0.a("shift should be positive");
        }
        if (i2 == 0) {
            return objArr;
        }
        int v = t20.v(i, i2);
        Object obj = objArr[v];
        obj.getClass();
        Object o = o((Object[]) obj, i, i2 - 5);
        if (v < 31) {
            int i3 = v + 1;
            if (objArr[i3] != null) {
                if (i(objArr)) {
                    Arrays.fill(objArr, i3, 32, (Object) null);
                }
                Object[] m = m();
                i8.N(objArr, m, 0, 0, i3);
                objArr = m;
            }
        }
        if (o != objArr[v]) {
            Object[] k = k(objArr);
            k[v] = o;
            return k;
        }
        return objArr;
    }

    public final Object[] p(Object[] objArr, int i, int i2, j1 j1Var) {
        Object[] p;
        int v = t20.v(i2 - 1, i);
        if (i == 5) {
            j1Var.a = objArr[v];
            p = null;
        } else {
            Object obj = objArr[v];
            obj.getClass();
            p = p((Object[]) obj, i - 5, i2, j1Var);
        }
        if (p == null && v == 0) {
            return null;
        }
        Object[] k = k(objArr);
        k[v] = p;
        return k;
    }

    public final void q(Object[] objArr, int i, int i2) {
        if (i2 == 0) {
            this.j = null;
            if (objArr == null) {
                objArr = new Object[0];
            }
            this.k = objArr;
            this.l = i;
            this.h = i2;
            return;
        }
        j1 j1Var = new j1(null);
        objArr.getClass();
        Object[] p = p(objArr, i2, i, j1Var);
        p.getClass();
        Object obj = j1Var.a;
        obj.getClass();
        this.k = (Object[]) obj;
        this.l = i;
        if (p[1] == null) {
            this.j = (Object[]) p[0];
            this.h = i2 - 5;
        } else {
            this.j = p;
            this.h = i2;
        }
    }

    public final Object[] r(Object[] objArr, int i, int i2, Iterator it) {
        boolean z;
        if (!it.hasNext()) {
            cn0.a("invalid buffersIterator");
        }
        if (i2 >= 0) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            cn0.a("negative shift");
        }
        if (i2 == 0) {
            return (Object[]) it.next();
        }
        Object[] k = k(objArr);
        int v = t20.v(i, i2);
        int i3 = i2 - 5;
        k[v] = r((Object[]) k[v], i, i3, it);
        while (true) {
            v++;
            if (v >= 32 || !it.hasNext()) {
                break;
            }
            k[v] = r((Object[]) k[v], 0, i3, it);
        }
        return k;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean removeAll(Collection collection) {
        return y(new a0(1, collection));
    }

    public final Object[] s(Object[] objArr, int i, Object[][] objArr2) {
        Object[] k;
        t tVar = new t(objArr2);
        int i2 = i >> 5;
        int i3 = this.h;
        if (i2 < (1 << i3)) {
            k = r(objArr, i, i3, tVar);
        } else {
            k = k(objArr);
        }
        while (tVar.hasNext()) {
            this.h += 5;
            k = n(k);
            int i4 = this.h;
            r(k, 1 << i4, i4, tVar);
        }
        return k;
    }

    @Override // java.util.AbstractList, java.util.List
    public final Object set(int i, Object obj) {
        m20.j(i, a());
        if (B() <= i) {
            Object[] k = k(this.k);
            if (k != this.k) {
                ((AbstractList) this).modCount++;
            }
            int i2 = i & 31;
            Object obj2 = k[i2];
            k[i2] = obj;
            this.k = k;
            return obj2;
        }
        j1 j1Var = new j1(null);
        Object[] objArr = this.j;
        objArr.getClass();
        this.j = C(objArr, this.h, i, obj, j1Var);
        return j1Var.a;
    }

    public final void t(Object[] objArr, Object[] objArr2, Object[] objArr3) {
        int i = this.l;
        int i2 = i >> 5;
        int i3 = this.h;
        if (i2 > (1 << i3)) {
            this.j = u(this.h + 5, n(objArr), objArr2);
            this.k = objArr3;
            this.h += 5;
            this.l++;
            return;
        }
        if (objArr == null) {
            this.j = objArr2;
            this.k = objArr3;
            this.l = i + 1;
        } else {
            this.j = u(i3, objArr, objArr2);
            this.k = objArr3;
            this.l++;
        }
    }

    public final Object[] u(int i, Object[] objArr, Object[] objArr2) {
        int v = t20.v(a() - 1, i);
        Object[] k = k(objArr);
        if (i == 5) {
            k[v] = objArr2;
            return k;
        }
        k[v] = u(i - 5, (Object[]) k[v], objArr2);
        return k;
    }

    public final int v(gv gvVar, Object[] objArr, int i, int i2, j1 j1Var, ArrayList arrayList, ArrayList arrayList2) {
        Object[] m;
        if (i(objArr)) {
            arrayList.add(objArr);
        }
        Object obj = j1Var.a;
        obj.getClass();
        Object[] objArr2 = (Object[]) obj;
        Object[] objArr3 = objArr2;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj2 = objArr[i3];
            if (!((Boolean) gvVar.e(obj2)).booleanValue()) {
                if (i2 == 32) {
                    if (!arrayList.isEmpty()) {
                        m = (Object[]) arrayList.remove(arrayList.size() - 1);
                    } else {
                        m = m();
                    }
                    objArr3 = m;
                    i2 = 0;
                }
                objArr3[i2] = obj2;
                i2++;
            }
        }
        j1Var.a = objArr3;
        if (objArr2 != objArr3) {
            arrayList2.add(objArr2);
        }
        return i2;
    }

    public final int w(gv gvVar, Object[] objArr, int i, j1 j1Var) {
        Object[] objArr2 = objArr;
        int i2 = i;
        boolean z = false;
        for (int i3 = 0; i3 < i; i3++) {
            Object obj = objArr[i3];
            if (((Boolean) gvVar.e(obj)).booleanValue()) {
                if (!z) {
                    objArr2 = k(objArr);
                    z = true;
                    i2 = i3;
                }
            } else if (z) {
                objArr2[i2] = obj;
                i2++;
            }
        }
        j1Var.a = objArr2;
        return i2;
    }

    public final int x(gv gvVar, int i, j1 j1Var) {
        int w = w(gvVar, this.k, i, j1Var);
        Object obj = j1Var.a;
        if (w == i) {
            return i;
        }
        obj.getClass();
        Object[] objArr = (Object[]) obj;
        Arrays.fill(objArr, w, i, (Object) null);
        this.k = objArr;
        this.l -= i - w;
        return w;
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0046, code lost:
    
        if (r0 != r8) goto L6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:4:0x0016, code lost:
    
        if (x(r1, r8, r5) != r8) goto L6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean y(defpackage.gv r16) {
        /*
            r15 = this;
            r1 = r16
            int r8 = r15.E()
            j1 r5 = new j1
            r9 = 0
            r5.<init>(r9)
            java.lang.Object[] r0 = r15.j
            r10 = 0
            r11 = 1
            if (r0 != 0) goto L1b
            int r0 = r15.x(r1, r8, r5)
            if (r0 == r8) goto Ld1
        L18:
            r10 = r11
            goto Ld1
        L1b:
            x r12 = r15.j(r10)
            r13 = 32
            r0 = r13
        L22:
            if (r0 != r13) goto L35
            boolean r2 = r12.hasNext()
            if (r2 == 0) goto L35
            java.lang.Object r0 = r12.next()
            java.lang.Object[] r0 = (java.lang.Object[]) r0
            int r0 = r15.w(r1, r0, r13, r5)
            goto L22
        L35:
            if (r0 != r13) goto L49
            int r0 = r15.x(r1, r8, r5)
            if (r0 != 0) goto L46
            java.lang.Object[] r1 = r15.j
            int r2 = r15.l
            int r3 = r15.h
            r15.q(r1, r2, r3)
        L46:
            if (r0 == r8) goto Ld1
            goto L18
        L49:
            int r2 = r12.e
            int r2 = r2 - r11
            int r14 = r2 << 5
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            r4 = r0
        L59:
            boolean r0 = r12.hasNext()
            if (r0 == 0) goto L70
            java.lang.Object r0 = r12.next()
            r2 = r0
            java.lang.Object[] r2 = (java.lang.Object[]) r2
            r3 = 32
            r0 = r15
            int r4 = r0.v(r1, r2, r3, r4, r5, r6, r7)
            r1 = r16
            goto L59
        L70:
            java.lang.Object[] r2 = r15.k
            r0 = r15
            r1 = r16
            r3 = r8
            int r1 = r0.v(r1, r2, r3, r4, r5, r6, r7)
            java.lang.Object r2 = r5.a
            r2.getClass()
            java.lang.Object[] r2 = (java.lang.Object[]) r2
            java.util.Arrays.fill(r2, r1, r13, r9)
            boolean r3 = r7.isEmpty()
            java.lang.Object[] r4 = r15.j
            if (r3 == 0) goto L90
            r4.getClass()
            goto L9a
        L90:
            int r3 = r15.h
            java.util.Iterator r5 = r7.iterator()
            java.lang.Object[] r4 = r15.r(r4, r14, r3, r5)
        L9a:
            int r3 = r7.size()
            int r3 = r3 << 5
            int r14 = r14 + r3
            r3 = r14 & 31
            if (r3 != 0) goto La6
            goto Lab
        La6:
            java.lang.String r3 = "invalid size"
            defpackage.cn0.a(r3)
        Lab:
            if (r14 != 0) goto Lb0
            r15.h = r10
            goto Lc8
        Lb0:
            int r3 = r14 + (-1)
        Lb2:
            int r5 = r15.h
            int r6 = r3 >> r5
            if (r6 != 0) goto Lc4
            int r5 = r5 + (-5)
            r15.h = r5
            r4 = r4[r10]
            r4.getClass()
            java.lang.Object[] r4 = (java.lang.Object[]) r4
            goto Lb2
        Lc4:
            java.lang.Object[] r9 = r15.o(r4, r3, r5)
        Lc8:
            r15.j = r9
            r15.k = r2
            int r14 = r14 + r1
            r15.l = r14
            goto L18
        Ld1:
            if (r10 == 0) goto Ld8
            int r1 = r15.modCount
            int r1 = r1 + r11
            r15.modCount = r1
        Ld8:
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.yl0.y(gv):boolean");
    }

    public final Object[] z(Object[] objArr, int i, int i2, j1 j1Var) {
        int v = t20.v(i2, i);
        int i3 = 31;
        if (i == 0) {
            Object obj = objArr[v];
            Object[] k = k(objArr);
            i8.N(objArr, k, v, v + 1, 32);
            k[31] = j1Var.a;
            j1Var.a = obj;
            return k;
        }
        if (objArr[31] == null) {
            i3 = t20.v(B() - 1, i);
        }
        Object[] k2 = k(objArr);
        int i4 = i - 5;
        int i5 = v + 1;
        if (i5 <= i3) {
            while (true) {
                Object obj2 = k2[i3];
                obj2.getClass();
                k2[i3] = z((Object[]) obj2, i4, 0, j1Var);
                if (i3 == i5) {
                    break;
                }
                i3--;
            }
        }
        Object obj3 = k2[v];
        obj3.getClass();
        k2[v] = z((Object[]) obj3, i4, i2, j1Var);
        return k2;
    }

    @Override // java.util.AbstractList, java.util.List
    public final ListIterator listIterator() {
        return listIterator(0);
    }

    @Override // java.util.AbstractList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean add(Object obj) {
        ((AbstractList) this).modCount++;
        int E = E();
        if (E < 32) {
            Object[] k = k(this.k);
            k[E] = obj;
            this.k = k;
            this.l = a() + 1;
        } else {
            t(this.j, this.k, n(obj));
        }
        return true;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public final boolean addAll(Collection collection) {
        if (collection.isEmpty()) {
            return false;
        }
        ((AbstractList) this).modCount++;
        int E = E();
        Iterator it = collection.iterator();
        if (32 - E >= collection.size()) {
            Object[] k = k(this.k);
            d(k, E, it);
            this.k = k;
            this.l = collection.size() + this.l;
            return true;
        }
        int size = ((collection.size() + E) - 1) / 32;
        Object[][] objArr = new Object[size];
        Object[] k2 = k(this.k);
        d(k2, E, it);
        objArr[0] = k2;
        for (int i = 1; i < size; i++) {
            Object[] m = m();
            d(m, 0, it);
            objArr[i] = m;
        }
        this.j = s(this.j, B(), objArr);
        Object[] m2 = m();
        d(m2, 0, it);
        this.k = m2;
        this.l = collection.size() + this.l;
        return true;
    }
}
