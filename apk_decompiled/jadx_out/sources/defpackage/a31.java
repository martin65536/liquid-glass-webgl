package defpackage;

import java.util.Arrays;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class a31 {
    public static final a31 e = new a31(0, 0, new Object[0], null);
    public int a;
    public int b;
    public final rt c;
    public Object[] d;

    public a31(int i, int i2, Object[] objArr, rt rtVar) {
        this.a = i;
        this.b = i2;
        this.c = rtVar;
        this.d = objArr;
    }

    public static a31 j(int i, Object obj, Object obj2, int i2, Object obj3, Object obj4, int i3, rt rtVar) {
        Object[] objArr;
        if (i3 > 30) {
            return new a31(0, 0, new Object[]{obj, obj2, obj3, obj4}, rtVar);
        }
        int u = m20.u(i, i3);
        int u2 = m20.u(i2, i3);
        if (u != u2) {
            if (u < u2) {
                objArr = new Object[]{obj, obj2, obj3, obj4};
            } else {
                objArr = new Object[]{obj3, obj4, obj, obj2};
            }
            return new a31((1 << u) | (1 << u2), 0, objArr, rtVar);
        }
        return new a31(0, 1 << u, new Object[]{j(i, obj, obj2, i2, obj3, obj4, i3 + 5, rtVar)}, rtVar);
    }

    public final Object[] a(int i, int i2, int i3, Object obj, Object obj2, int i4, rt rtVar) {
        int i5;
        Object obj3 = this.d[i];
        if (obj3 != null) {
            i5 = obj3.hashCode();
        } else {
            i5 = 0;
        }
        a31 j = j(i5, obj3, x(i), i3, obj, obj2, i4 + 5, rtVar);
        int t = t(i2);
        int i6 = t + 1;
        Object[] objArr = this.d;
        Object[] objArr2 = new Object[objArr.length - 1];
        i8.P(objArr, objArr2, 0, i, 6);
        i8.N(objArr, objArr2, i, i + 2, i6);
        objArr2[t - 1] = j;
        i8.N(objArr, objArr2, t, i6, objArr.length);
        return objArr2;
    }

    public final int b() {
        if (this.b == 0) {
            return this.d.length / 2;
        }
        int bitCount = Integer.bitCount(this.a);
        int length = this.d.length;
        for (int i = bitCount * 2; i < length; i++) {
            bitCount += s(i).b();
        }
        return bitCount;
    }

    public final boolean c(Object obj) {
        w10 H = n30.H(n30.K(0, this.d.length));
        int i = H.e;
        int i2 = H.f;
        int i3 = H.g;
        if ((i3 > 0 && i <= i2) || (i3 < 0 && i2 <= i)) {
            while (!o20.e(obj, this.d[i])) {
                if (i != i2) {
                    i += i3;
                }
            }
            return true;
        }
        return false;
    }

    public final boolean d(int i, int i2, Object obj) {
        int u = 1 << m20.u(i, i2);
        if (h(u)) {
            return o20.e(obj, this.d[f(u)]);
        }
        if (i(u)) {
            a31 s = s(t(u));
            if (i2 == 30) {
                return s.c(obj);
            }
            return s.d(i, i2 + 5, obj);
        }
        return false;
    }

    public final boolean e(a31 a31Var) {
        if (this != a31Var) {
            if (this.b == a31Var.b && this.a == a31Var.a) {
                int length = this.d.length;
                for (int i = 0; i < length; i++) {
                    if (this.d[i] == a31Var.d[i]) {
                    }
                }
                return true;
            }
            return false;
        }
        return true;
    }

    public final int f(int i) {
        return Integer.bitCount(this.a & (i - 1)) * 2;
    }

    public final Object g(int i, int i2, Object obj) {
        int u = 1 << m20.u(i, i2);
        if (h(u)) {
            int f = f(u);
            if (o20.e(obj, this.d[f])) {
                return x(f);
            }
            return null;
        }
        if (i(u)) {
            a31 s = s(t(u));
            if (i2 == 30) {
                w10 H = n30.H(n30.K(0, s.d.length));
                int i3 = H.e;
                int i4 = H.f;
                int i5 = H.g;
                if ((i5 > 0 && i3 <= i4) || (i5 < 0 && i4 <= i3)) {
                    while (!o20.e(obj, s.d[i3])) {
                        if (i3 != i4) {
                            i3 += i5;
                        } else {
                            return null;
                        }
                    }
                    return s.x(i3);
                }
                return null;
            }
            return s.g(i, i2 + 5, obj);
        }
        return null;
    }

    public final boolean h(int i) {
        if ((this.a & i) != 0) {
            return true;
        }
        return false;
    }

    public final boolean i(int i) {
        if ((this.b & i) != 0) {
            return true;
        }
        return false;
    }

    public final a31 k(int i, ol0 ol0Var) {
        ol0Var.a(ol0Var.i - 1);
        ol0Var.g = x(i);
        Object[] objArr = this.d;
        if (objArr.length == 2) {
            return null;
        }
        if (this.c == ol0Var.e) {
            this.d = m20.h(i, objArr);
            return this;
        }
        return new a31(0, 0, m20.h(i, objArr), ol0Var.e);
    }

    public final a31 l(int i, Object obj, Object obj2, int i2, ol0 ol0Var) {
        ol0 ol0Var2;
        a31 l;
        int u = 1 << m20.u(i, i2);
        boolean h = h(u);
        rt rtVar = this.c;
        if (h) {
            int f = f(u);
            if (o20.e(obj, this.d[f])) {
                ol0Var.g = x(f);
                if (x(f) == obj2) {
                    return this;
                }
                if (rtVar == ol0Var.e) {
                    this.d[f + 1] = obj2;
                    return this;
                }
                ol0Var.h++;
                Object[] objArr = this.d;
                Object[] copyOf = Arrays.copyOf(objArr, objArr.length);
                copyOf[f + 1] = obj2;
                return new a31(this.a, this.b, copyOf, ol0Var.e);
            }
            ol0Var.a(ol0Var.i + 1);
            rt rtVar2 = ol0Var.e;
            if (rtVar == rtVar2) {
                this.d = a(f, u, i, obj, obj2, i2, rtVar2);
                this.a ^= u;
                this.b |= u;
                return this;
            }
            return new a31(this.a ^ u, this.b | u, a(f, u, i, obj, obj2, i2, rtVar2), rtVar2);
        }
        if (i(u)) {
            int t = t(u);
            a31 s = s(t);
            if (i2 == 30) {
                w10 H = n30.H(n30.K(0, s.d.length));
                int i3 = H.e;
                int i4 = H.f;
                int i5 = H.g;
                if ((i5 > 0 && i3 <= i4) || (i5 < 0 && i4 <= i3)) {
                    while (!o20.e(obj, s.d[i3])) {
                        if (i3 != i4) {
                            i3 += i5;
                        }
                    }
                    ol0Var.g = s.x(i3);
                    if (s.c == ol0Var.e) {
                        s.d[i3 + 1] = obj2;
                        l = s;
                    } else {
                        ol0Var.h++;
                        Object[] objArr2 = s.d;
                        Object[] copyOf2 = Arrays.copyOf(objArr2, objArr2.length);
                        copyOf2[i3 + 1] = obj2;
                        l = new a31(0, 0, copyOf2, ol0Var.e);
                    }
                    ol0Var2 = ol0Var;
                }
                ol0Var.a(ol0Var.i + 1);
                l = new a31(0, 0, m20.g(s.d, 0, obj, obj2), ol0Var.e);
                ol0Var2 = ol0Var;
            } else {
                ol0Var2 = ol0Var;
                l = s.l(i, obj, obj2, i2 + 5, ol0Var2);
            }
            if (s == l) {
                return this;
            }
            return r(t, l, ol0Var2.e);
        }
        ol0Var.a(ol0Var.i + 1);
        rt rtVar3 = ol0Var.e;
        int f2 = f(u);
        Object[] objArr3 = this.d;
        if (rtVar == rtVar3) {
            this.d = m20.g(objArr3, f2, obj, obj2);
            this.a |= u;
            return this;
        }
        return new a31(this.a | u, this.b, m20.g(objArr3, f2, obj, obj2), rtVar3);
    }

    public final a31 m(a31 a31Var, int i, lm lmVar, ol0 ol0Var) {
        a31 a31Var2;
        Object[] objArr;
        int i2;
        int i3;
        a31 j;
        int i4;
        int i5;
        int i6;
        if (this == a31Var) {
            lmVar.a += b();
            return this;
        }
        int i7 = 0;
        if (i > 30) {
            rt rtVar = ol0Var.e;
            int i8 = a31Var.b;
            Object[] objArr2 = this.d;
            Object[] copyOf = Arrays.copyOf(objArr2, objArr2.length + a31Var.d.length);
            int length = this.d.length;
            w10 H = n30.H(n30.K(0, a31Var.d.length));
            int i9 = H.e;
            int i10 = H.f;
            int i11 = H.g;
            if ((i11 > 0 && i9 <= i10) || (i11 < 0 && i10 <= i9)) {
                while (true) {
                    if (!c(a31Var.d[i9])) {
                        Object[] objArr3 = a31Var.d;
                        copyOf[length] = objArr3[i9];
                        copyOf[length + 1] = objArr3[i9 + 1];
                        length += 2;
                    } else {
                        lmVar.a++;
                    }
                    if (i9 == i10) {
                        break;
                    }
                    i9 += i11;
                }
            }
            if (length != this.d.length) {
                if (length == a31Var.d.length) {
                    return a31Var;
                }
                if (length == copyOf.length) {
                    return new a31(0, 0, copyOf, rtVar);
                }
                return new a31(0, 0, Arrays.copyOf(copyOf, length), rtVar);
            }
        } else {
            int i12 = this.b | a31Var.b;
            int i13 = this.a;
            int i14 = a31Var.a;
            int i15 = (i13 ^ i14) & (~i12);
            int i16 = i13 & i14;
            int i17 = i15;
            while (i16 != 0) {
                int lowestOneBit = Integer.lowestOneBit(i16);
                if (o20.e(this.d[f(lowestOneBit)], a31Var.d[a31Var.f(lowestOneBit)])) {
                    i17 |= lowestOneBit;
                } else {
                    i12 |= lowestOneBit;
                }
                i16 ^= lowestOneBit;
            }
            if ((i12 & i17) != 0) {
                cn0.b("Check failed.");
            }
            if (o20.e(this.c, ol0Var.e) && this.a == i17 && this.b == i12) {
                a31Var2 = this;
            } else {
                a31Var2 = new a31(i17, i12, new Object[Integer.bitCount(i12) + (Integer.bitCount(i17) * 2)], null);
            }
            int i18 = i12;
            int i19 = 0;
            while (i18 != 0) {
                int lowestOneBit2 = Integer.lowestOneBit(i18);
                Object[] objArr4 = a31Var2.d;
                int length2 = (objArr4.length - 1) - i19;
                if (i(lowestOneBit2)) {
                    j = s(t(lowestOneBit2));
                    if (a31Var.i(lowestOneBit2)) {
                        j = j.m(a31Var.s(a31Var.t(lowestOneBit2)), i + 5, lmVar, ol0Var);
                        objArr = objArr4;
                    } else if (a31Var.h(lowestOneBit2)) {
                        int f = a31Var.f(lowestOneBit2);
                        Object obj = a31Var.d[f];
                        Object x = a31Var.x(f);
                        int i20 = ol0Var.i;
                        if (obj != null) {
                            i6 = obj.hashCode();
                        } else {
                            i6 = i7;
                        }
                        int i21 = i6;
                        objArr = objArr4;
                        j = j.l(i21, obj, x, i + 5, ol0Var);
                        if (ol0Var.i == i20) {
                            lmVar.a++;
                        }
                    } else {
                        objArr = objArr4;
                    }
                } else {
                    objArr = objArr4;
                    if (a31Var.i(lowestOneBit2)) {
                        a31 s = a31Var.s(a31Var.t(lowestOneBit2));
                        if (h(lowestOneBit2)) {
                            int f2 = f(lowestOneBit2);
                            Object obj2 = this.d[f2];
                            if (obj2 != null) {
                                i4 = obj2.hashCode();
                            } else {
                                i4 = 0;
                            }
                            int i22 = i + 5;
                            if (s.d(i4, i22, obj2)) {
                                lmVar.a++;
                            } else {
                                Object x2 = x(f2);
                                if (obj2 != null) {
                                    i5 = obj2.hashCode();
                                } else {
                                    i5 = 0;
                                }
                                j = s.l(i5, obj2, x2, i22, ol0Var);
                            }
                        }
                        j = s;
                    } else {
                        int f3 = f(lowestOneBit2);
                        Object obj3 = this.d[f3];
                        Object x3 = x(f3);
                        int f4 = a31Var.f(lowestOneBit2);
                        Object obj4 = a31Var.d[f4];
                        Object x4 = a31Var.x(f4);
                        if (obj3 != null) {
                            i2 = obj3.hashCode();
                        } else {
                            i2 = 0;
                        }
                        if (obj4 != null) {
                            i3 = obj4.hashCode();
                        } else {
                            i3 = 0;
                        }
                        j = j(i2, obj3, x3, i3, obj4, x4, i + 5, ol0Var.e);
                    }
                }
                objArr[length2] = j;
                i19++;
                i18 ^= lowestOneBit2;
                i7 = 0;
            }
            int i23 = 0;
            while (i17 != 0) {
                int lowestOneBit3 = Integer.lowestOneBit(i17);
                int i24 = i23 * 2;
                if (!a31Var.h(lowestOneBit3)) {
                    int f5 = f(lowestOneBit3);
                    Object[] objArr5 = a31Var2.d;
                    objArr5[i24] = this.d[f5];
                    objArr5[i24 + 1] = x(f5);
                } else {
                    int f6 = a31Var.f(lowestOneBit3);
                    Object[] objArr6 = a31Var2.d;
                    objArr6[i24] = a31Var.d[f6];
                    objArr6[i24 + 1] = a31Var.x(f6);
                    if (h(lowestOneBit3)) {
                        lmVar.a++;
                    }
                }
                i23++;
                i17 ^= lowestOneBit3;
            }
            if (!e(a31Var2)) {
                if (a31Var.e(a31Var2)) {
                    return a31Var;
                }
                return a31Var2;
            }
        }
        return this;
    }

    public final a31 n(int i, Object obj, int i2, ol0 ol0Var) {
        a31 n;
        int u = 1 << m20.u(i, i2);
        if (h(u)) {
            int f = f(u);
            if (o20.e(obj, this.d[f])) {
                return p(f, u, ol0Var);
            }
        } else if (i(u)) {
            int t = t(u);
            a31 s = s(t);
            if (i2 == 30) {
                w10 H = n30.H(n30.K(0, s.d.length));
                int i3 = H.e;
                int i4 = H.f;
                int i5 = H.g;
                if ((i5 > 0 && i3 <= i4) || (i5 < 0 && i4 <= i3)) {
                    while (!o20.e(obj, s.d[i3])) {
                        if (i3 != i4) {
                            i3 += i5;
                        }
                    }
                    n = s.k(i3, ol0Var);
                }
                n = s;
                break;
            }
            n = s.n(i, obj, i2 + 5, ol0Var);
            return q(s, n, t, u, ol0Var.e);
        }
        return this;
    }

    public final a31 o(int i, Object obj, Object obj2, int i2, ol0 ol0Var) {
        ol0 ol0Var2;
        a31 o;
        int u = 1 << m20.u(i, i2);
        if (h(u)) {
            int f = f(u);
            if (o20.e(obj, this.d[f]) && o20.e(obj2, x(f))) {
                return p(f, u, ol0Var);
            }
            return this;
        }
        if (i(u)) {
            int t = t(u);
            a31 s = s(t);
            if (i2 == 30) {
                w10 H = n30.H(n30.K(0, s.d.length));
                int i3 = H.e;
                int i4 = H.f;
                int i5 = H.g;
                if ((i5 > 0 && i3 <= i4) || (i5 < 0 && i4 <= i3)) {
                    while (true) {
                        if (o20.e(obj, s.d[i3]) && o20.e(obj2, s.x(i3))) {
                            o = s.k(i3, ol0Var);
                            break;
                        }
                        if (i3 == i4) {
                            break;
                        }
                        i3 += i5;
                    }
                }
                o = s;
                ol0Var2 = ol0Var;
            } else {
                ol0Var2 = ol0Var;
                o = s.o(i, obj, obj2, i2 + 5, ol0Var2);
            }
            return q(s, o, t, u, ol0Var2.e);
        }
        return this;
    }

    public final a31 p(int i, int i2, ol0 ol0Var) {
        ol0Var.a(ol0Var.i - 1);
        ol0Var.g = x(i);
        Object[] objArr = this.d;
        if (objArr.length == 2) {
            return null;
        }
        if (this.c == ol0Var.e) {
            this.d = m20.h(i, objArr);
            this.a ^= i2;
            return this;
        }
        return new a31(i2 ^ this.a, this.b, m20.h(i, objArr), ol0Var.e);
    }

    public final a31 q(a31 a31Var, a31 a31Var2, int i, int i2, rt rtVar) {
        rt rtVar2 = this.c;
        if (a31Var2 == null) {
            Object[] objArr = this.d;
            if (objArr.length == 1) {
                return null;
            }
            if (rtVar2 == rtVar) {
                this.d = m20.i(i, objArr);
                this.b ^= i2;
                return this;
            }
            return new a31(this.a, this.b ^ i2, m20.i(i, objArr), rtVar);
        }
        if (rtVar2 != rtVar && a31Var == a31Var2) {
            return this;
        }
        return r(i, a31Var2, rtVar);
    }

    public final a31 r(int i, a31 a31Var, rt rtVar) {
        Object[] objArr = this.d;
        if (objArr.length == 1 && a31Var.d.length == 2 && a31Var.b == 0) {
            a31Var.a = this.b;
            return a31Var;
        }
        if (this.c == rtVar) {
            objArr[i] = a31Var;
            return this;
        }
        Object[] copyOf = Arrays.copyOf(objArr, objArr.length);
        copyOf[i] = a31Var;
        return new a31(this.a, this.b, copyOf, rtVar);
    }

    public final a31 s(int i) {
        Object obj = this.d[i];
        obj.getClass();
        return (a31) obj;
    }

    public final int t(int i) {
        return (this.d.length - 1) - Integer.bitCount(this.b & (i - 1));
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x00c5, code lost:
    
        if (r13 != null) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00d1, code lost:
    
        r13.b = w(r11, r4, (defpackage.a31) r13.b);
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00db, code lost:
    
        return r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00ce, code lost:
    
        if (r13 == null) goto L35;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final defpackage.pu u(int r12, int r13, java.lang.Object r14, java.lang.Object r15) {
        /*
            Method dump skipped, instructions count: 246
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.a31.u(int, int, java.lang.Object, java.lang.Object):pu");
    }

    public final a31 v(int i, int i2, Object obj) {
        a31 v;
        int u = 1 << m20.u(i, i2);
        if (h(u)) {
            int f = f(u);
            if (o20.e(obj, this.d[f])) {
                Object[] objArr = this.d;
                if (objArr.length != 2) {
                    return new a31(this.a ^ u, this.b, m20.h(f, objArr), null);
                }
            } else {
                return this;
            }
        } else if (i(u)) {
            int t = t(u);
            a31 s = s(t);
            if (i2 == 30) {
                w10 H = n30.H(n30.K(0, s.d.length));
                int i3 = H.e;
                int i4 = H.f;
                int i5 = H.g;
                if ((i5 > 0 && i3 <= i4) || (i5 < 0 && i4 <= i3)) {
                    while (!o20.e(obj, s.d[i3])) {
                        if (i3 != i4) {
                            i3 += i5;
                        }
                    }
                    Object[] objArr2 = s.d;
                    if (objArr2.length == 2) {
                        v = null;
                    } else {
                        v = new a31(0, 0, m20.h(i3, objArr2), null);
                    }
                }
                v = s;
                break;
            }
            v = s.v(i, i2 + 5, obj);
            if (v == null) {
                Object[] objArr3 = this.d;
                if (objArr3.length != 1) {
                    return new a31(this.a, this.b ^ u, m20.i(t, objArr3), null);
                }
            } else {
                if (s != v) {
                    return w(t, u, v);
                }
                return this;
            }
        } else {
            return this;
        }
        return null;
    }

    public final a31 w(int i, int i2, a31 a31Var) {
        Object[] objArr = a31Var.d;
        if (objArr.length == 2 && a31Var.b == 0) {
            if (this.d.length == 1) {
                a31Var.a = this.b;
                return a31Var;
            }
            int f = f(i2);
            Object[] objArr2 = this.d;
            Object obj = objArr[0];
            Object obj2 = objArr[1];
            Object[] copyOf = Arrays.copyOf(objArr2, objArr2.length + 1);
            i8.N(copyOf, copyOf, i + 2, i + 1, objArr2.length);
            i8.N(copyOf, copyOf, f + 2, f, i);
            copyOf[f] = obj;
            copyOf[f + 1] = obj2;
            return new a31(this.a ^ i2, this.b ^ i2, copyOf, null);
        }
        Object[] objArr3 = this.d;
        Object[] copyOf2 = Arrays.copyOf(objArr3, objArr3.length);
        copyOf2[i] = a31Var;
        return new a31(this.a, this.b, copyOf2, null);
    }

    public final Object x(int i) {
        return this.d[i + 1];
    }
}
