package defpackage;

import android.os.Trace;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class yh implements sh {
    public int A;
    public final th e;
    public final r7 f;
    public final AtomicReference g = new AtomicReference(null);
    public final Object h = new Object();
    public final ye0 i;
    public final rw0 j;
    public final ve0 k;
    public final we0 l;
    public final we0 m;
    public final ve0 n;
    public final cd o;
    public final cd p;
    public final ve0 q;
    public ve0 r;
    public boolean s;
    public iw0 t;
    public hl0 u;
    public yh v;
    public int w;
    public final j2 x;
    public final mp0 y;
    public final bw z;

    public yh(th thVar, r7 r7Var) {
        this.e = thVar;
        this.f = r7Var;
        ye0 ye0Var = new ye0(new we0());
        this.i = ye0Var;
        rw0 rw0Var = new rw0();
        if (thVar.d()) {
            rw0Var.o = new he0();
        }
        if (thVar.f()) {
            rw0Var.b();
        }
        this.j = rw0Var;
        this.k = t20.n();
        this.l = new we0();
        this.m = new we0();
        this.n = t20.n();
        cd cdVar = new cd();
        this.o = cdVar;
        cd cdVar2 = new cd();
        this.p = cdVar2;
        this.q = t20.n();
        this.r = t20.n();
        j2 j2Var = new j2(3, thVar);
        this.x = j2Var;
        this.y = new mp0();
        bw bwVar = new bw(r7Var, thVar, tw0.d(rw0Var), ye0Var, cdVar, cdVar2, j2Var, this);
        thVar.p(bwVar);
        this.z = bwVar;
    }

    public final void A(kv kvVar) {
        boolean i = i();
        q();
        th thVar = this.e;
        if (i) {
            bw bwVar = this.z;
            bwVar.z = 0;
            bwVar.y = true;
            thVar.a(this, kvVar);
            bwVar.s();
            return;
        }
        thVar.a(this, kvVar);
    }

    public final void a() {
        this.g.set(null);
        this.o.w.D();
        this.p.w.D();
        ye0 ye0Var = this.i;
        if (!ye0Var.e.g()) {
            mp0 mp0Var = this.y;
            try {
                mp0Var.g(ye0Var, this.z.z());
                mp0Var.b();
            } finally {
                mp0Var.a();
            }
        }
    }

    public final void b(Object obj, boolean z) {
        Object g = this.k.g(obj);
        if (g != null) {
            boolean z2 = g instanceof we0;
            w20 w20Var = w20.e;
            we0 we0Var = this.l;
            we0 we0Var2 = this.m;
            ve0 ve0Var = this.q;
            if (z2) {
                we0 we0Var3 = (we0) g;
                Object[] objArr = we0Var3.b;
                long[] jArr = we0Var3.a;
                int length = jArr.length - 2;
                if (length >= 0) {
                    int i = 0;
                    while (true) {
                        long j = jArr[i];
                        if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                            int i2 = 8 - ((~(i - length)) >>> 31);
                            for (int i3 = 0; i3 < i2; i3++) {
                                if ((255 & j) < 128) {
                                    mo0 mo0Var = (mo0) objArr[(i << 3) + i3];
                                    if (!t20.I(ve0Var, obj, mo0Var) && mo0Var.b(obj) != w20Var) {
                                        if (mo0Var.g != null && !z) {
                                            we0Var2.a(mo0Var);
                                        } else {
                                            we0Var.a(mo0Var);
                                        }
                                    }
                                }
                                j >>= 8;
                            }
                            if (i2 != 8) {
                                return;
                            }
                        }
                        if (i != length) {
                            i++;
                        } else {
                            return;
                        }
                    }
                }
            } else {
                mo0 mo0Var2 = (mo0) g;
                if (!t20.I(ve0Var, obj, mo0Var2) && mo0Var2.b(obj) != w20Var) {
                    if (mo0Var2.g != null && !z) {
                        we0Var2.a(mo0Var2);
                    } else {
                        we0Var.a(mo0Var2);
                    }
                }
            }
        }
    }

    public final void c(Set set, boolean z) {
        long j;
        long j2;
        long j3;
        char c;
        long[] jArr;
        long[] jArr2;
        long j4;
        boolean c2;
        long[] jArr3;
        long j5;
        long[] jArr4;
        long[] jArr5;
        long j6;
        boolean z2;
        long[] jArr6;
        long j7;
        long[] jArr7;
        long[] jArr8;
        char c3;
        long j8;
        int i;
        int i2;
        long[] jArr9;
        boolean z3 = set instanceof bt0;
        ve0 ve0Var = this.n;
        Object obj = null;
        int i3 = 8;
        if (z3) {
            we0 we0Var = ((bt0) set).e;
            Object[] objArr = we0Var.b;
            long[] jArr10 = we0Var.a;
            int length = jArr10.length - 2;
            if (length >= 0) {
                int i4 = 0;
                j = 128;
                j2 = 255;
                while (true) {
                    long j9 = jArr10[i4];
                    char c4 = 7;
                    j3 = -9187201950435737472L;
                    if ((((~j9) << 7) & j9 & (-9187201950435737472L)) != -9187201950435737472L) {
                        int i5 = 8 - ((~(i4 - length)) >>> 31);
                        int i6 = 0;
                        while (i6 < i5) {
                            if ((j9 & 255) < 128) {
                                Object obj2 = objArr[(i4 << 3) + i6];
                                c3 = c4;
                                if (obj2 instanceof mo0) {
                                    ((mo0) obj2).b(obj);
                                } else {
                                    b(obj2, z);
                                    Object g = ve0Var.g(obj2);
                                    if (g != null) {
                                        if (g instanceof we0) {
                                            we0 we0Var2 = (we0) g;
                                            Object[] objArr2 = we0Var2.b;
                                            long[] jArr11 = we0Var2.a;
                                            int length2 = jArr11.length - 2;
                                            if (length2 >= 0) {
                                                int i7 = i3;
                                                i = length;
                                                int i8 = 0;
                                                while (true) {
                                                    long j10 = jArr11[i8];
                                                    j8 = j9;
                                                    long[] jArr12 = jArr11;
                                                    if ((((~j10) << c3) & j10 & (-9187201950435737472L)) != -9187201950435737472L) {
                                                        int i9 = 8 - ((~(i8 - length2)) >>> 31);
                                                        int i10 = 0;
                                                        while (i10 < i9) {
                                                            if ((j10 & 255) < 128) {
                                                                jArr9 = jArr10;
                                                                b((ym) objArr2[(i8 << 3) + i10], z);
                                                            } else {
                                                                jArr9 = jArr10;
                                                            }
                                                            j10 >>= i7;
                                                            i10++;
                                                            jArr10 = jArr9;
                                                        }
                                                        jArr8 = jArr10;
                                                        if (i9 != i7) {
                                                            break;
                                                        }
                                                    } else {
                                                        jArr8 = jArr10;
                                                    }
                                                    if (i8 == length2) {
                                                        break;
                                                    }
                                                    i8++;
                                                    jArr11 = jArr12;
                                                    j9 = j8;
                                                    jArr10 = jArr8;
                                                    i7 = 8;
                                                }
                                            }
                                        } else {
                                            jArr8 = jArr10;
                                            j8 = j9;
                                            i = length;
                                            b((ym) g, z);
                                        }
                                        i2 = 8;
                                    }
                                }
                                jArr8 = jArr10;
                                j8 = j9;
                                i = length;
                                i2 = 8;
                            } else {
                                jArr8 = jArr10;
                                c3 = c4;
                                j8 = j9;
                                i = length;
                                i2 = i3;
                            }
                            j9 = j8 >> i2;
                            i6++;
                            length = i;
                            i3 = i2;
                            c4 = c3;
                            jArr10 = jArr8;
                            obj = null;
                        }
                        jArr7 = jArr10;
                        c = c4;
                        int i11 = length;
                        if (i5 != i3) {
                            break;
                        } else {
                            length = i11;
                        }
                    } else {
                        jArr7 = jArr10;
                        c = 7;
                    }
                    if (i4 == length) {
                        break;
                    }
                    i4++;
                    jArr10 = jArr7;
                    obj = null;
                    i3 = 8;
                }
            } else {
                j = 128;
                j2 = 255;
                j3 = -9187201950435737472L;
                c = 7;
            }
        } else {
            j = 128;
            j2 = 255;
            j3 = -9187201950435737472L;
            c = 7;
            for (Object obj3 : set) {
                if (obj3 instanceof mo0) {
                    ((mo0) obj3).b(null);
                } else {
                    b(obj3, z);
                    Object g2 = ve0Var.g(obj3);
                    if (g2 != null) {
                        if (g2 instanceof we0) {
                            we0 we0Var3 = (we0) g2;
                            Object[] objArr3 = we0Var3.b;
                            long[] jArr13 = we0Var3.a;
                            int length3 = jArr13.length - 2;
                            if (length3 >= 0) {
                                int i12 = 0;
                                while (true) {
                                    long j11 = jArr13[i12];
                                    if ((((~j11) << 7) & j11 & (-9187201950435737472L)) != -9187201950435737472L) {
                                        int i13 = 8 - ((~(i12 - length3)) >>> 31);
                                        for (int i14 = 0; i14 < i13; i14++) {
                                            if ((j11 & 255) < 128) {
                                                b((ym) objArr3[(i12 << 3) + i14], z);
                                            }
                                            j11 >>= 8;
                                        }
                                        if (i13 != 8) {
                                            break;
                                        }
                                    }
                                    if (i12 != length3) {
                                        i12++;
                                    }
                                }
                            }
                        } else {
                            b((ym) g2, z);
                        }
                    }
                }
            }
        }
        ve0 ve0Var2 = this.k;
        we0 we0Var4 = this.l;
        if (z) {
            we0 we0Var5 = this.m;
            if (we0Var5.h()) {
                long[] jArr14 = ve0Var2.a;
                int length4 = jArr14.length - 2;
                if (length4 >= 0) {
                    int i15 = 0;
                    while (true) {
                        long j12 = jArr14[i15];
                        if ((((~j12) << c) & j12 & j3) != j3) {
                            int i16 = 8 - ((~(i15 - length4)) >>> 31);
                            int i17 = 0;
                            while (i17 < i16) {
                                if ((j12 & j2) < j) {
                                    int i18 = (i15 << 3) + i17;
                                    Object obj4 = ve0Var2.b[i18];
                                    Object obj5 = ve0Var2.c[i18];
                                    if (obj5 instanceof we0) {
                                        we0 we0Var6 = (we0) obj5;
                                        Object[] objArr4 = we0Var6.b;
                                        long[] jArr15 = we0Var6.a;
                                        int length5 = jArr15.length - 2;
                                        if (length5 >= 0) {
                                            j6 = j12;
                                            int i19 = 0;
                                            while (true) {
                                                long j13 = jArr15[i19];
                                                Object[] objArr5 = objArr4;
                                                long[] jArr16 = jArr15;
                                                if ((((~j13) << c) & j13 & j3) != j3) {
                                                    int i20 = 8 - ((~(i19 - length5)) >>> 31);
                                                    int i21 = 0;
                                                    while (i21 < i20) {
                                                        if ((j13 & j2) < j) {
                                                            jArr6 = jArr14;
                                                            int i22 = (i19 << 3) + i21;
                                                            j7 = j13;
                                                            mo0 mo0Var = (mo0) objArr5[i22];
                                                            if (we0Var5.c(mo0Var) || we0Var4.c(mo0Var)) {
                                                                we0Var6.m(i22);
                                                            }
                                                        } else {
                                                            jArr6 = jArr14;
                                                            j7 = j13;
                                                        }
                                                        j13 = j7 >> 8;
                                                        i21++;
                                                        jArr14 = jArr6;
                                                    }
                                                    jArr5 = jArr14;
                                                    if (i20 != 8) {
                                                        break;
                                                    }
                                                } else {
                                                    jArr5 = jArr14;
                                                }
                                                if (i19 == length5) {
                                                    break;
                                                }
                                                i19++;
                                                objArr4 = objArr5;
                                                jArr15 = jArr16;
                                                jArr14 = jArr5;
                                            }
                                        } else {
                                            jArr5 = jArr14;
                                            j6 = j12;
                                        }
                                        z2 = we0Var6.g();
                                    } else {
                                        jArr5 = jArr14;
                                        j6 = j12;
                                        obj5.getClass();
                                        mo0 mo0Var2 = (mo0) obj5;
                                        if (!we0Var5.c(mo0Var2) && !we0Var4.c(mo0Var2)) {
                                            z2 = false;
                                        } else {
                                            z2 = true;
                                        }
                                    }
                                    if (z2) {
                                        ve0Var2.l(i18);
                                    }
                                } else {
                                    jArr5 = jArr14;
                                    j6 = j12;
                                }
                                j12 = j6 >> 8;
                                i17++;
                                jArr14 = jArr5;
                            }
                            jArr4 = jArr14;
                            if (i16 != 8) {
                                break;
                            }
                        } else {
                            jArr4 = jArr14;
                        }
                        if (i15 == length4) {
                            break;
                        }
                        i15++;
                        jArr14 = jArr4;
                    }
                }
                we0Var5.b();
                h();
                return;
            }
        }
        if (we0Var4.h()) {
            long[] jArr17 = ve0Var2.a;
            int length6 = jArr17.length - 2;
            if (length6 >= 0) {
                int i23 = 0;
                while (true) {
                    long j14 = jArr17[i23];
                    if ((((~j14) << c) & j14 & j3) != j3) {
                        int i24 = 8 - ((~(i23 - length6)) >>> 31);
                        int i25 = 0;
                        while (i25 < i24) {
                            if ((j14 & j2) < j) {
                                int i26 = (i23 << 3) + i25;
                                Object obj6 = ve0Var2.b[i26];
                                Object obj7 = ve0Var2.c[i26];
                                if (obj7 instanceof we0) {
                                    we0 we0Var7 = (we0) obj7;
                                    Object[] objArr6 = we0Var7.b;
                                    long[] jArr18 = we0Var7.a;
                                    int length7 = jArr18.length - 2;
                                    if (length7 >= 0) {
                                        j4 = j14;
                                        int i27 = 0;
                                        while (true) {
                                            long j15 = jArr18[i27];
                                            Object[] objArr7 = objArr6;
                                            long[] jArr19 = jArr18;
                                            if ((((~j15) << c) & j15 & j3) != j3) {
                                                int i28 = 8 - ((~(i27 - length7)) >>> 31);
                                                int i29 = 0;
                                                while (i29 < i28) {
                                                    if ((j15 & j2) < j) {
                                                        jArr3 = jArr17;
                                                        int i30 = (i27 << 3) + i29;
                                                        j5 = j15;
                                                        if (we0Var4.c((mo0) objArr7[i30])) {
                                                            we0Var7.m(i30);
                                                        }
                                                    } else {
                                                        jArr3 = jArr17;
                                                        j5 = j15;
                                                    }
                                                    j15 = j5 >> 8;
                                                    i29++;
                                                    jArr17 = jArr3;
                                                }
                                                jArr2 = jArr17;
                                                if (i28 != 8) {
                                                    break;
                                                }
                                            } else {
                                                jArr2 = jArr17;
                                            }
                                            if (i27 == length7) {
                                                break;
                                            }
                                            i27++;
                                            objArr6 = objArr7;
                                            jArr18 = jArr19;
                                            jArr17 = jArr2;
                                        }
                                    } else {
                                        jArr2 = jArr17;
                                        j4 = j14;
                                    }
                                    c2 = we0Var7.g();
                                } else {
                                    jArr2 = jArr17;
                                    j4 = j14;
                                    obj7.getClass();
                                    c2 = we0Var4.c((mo0) obj7);
                                }
                                if (c2) {
                                    ve0Var2.l(i26);
                                }
                            } else {
                                jArr2 = jArr17;
                                j4 = j14;
                            }
                            j14 = j4 >> 8;
                            i25++;
                            jArr17 = jArr2;
                        }
                        jArr = jArr17;
                        if (i24 != 8) {
                            break;
                        }
                    } else {
                        jArr = jArr17;
                    }
                    if (i23 == length6) {
                        break;
                    }
                    i23++;
                    jArr17 = jArr;
                }
            }
            h();
            we0Var4.b();
        }
    }

    public final void d() {
        synchronized (this.h) {
            try {
                e(this.o);
                o();
            } catch (Throwable th) {
                try {
                    if (!this.i.e.g()) {
                        mp0 mp0Var = this.y;
                        try {
                            mp0Var.g(this.i, this.z.z());
                            mp0Var.b();
                            mp0Var.a();
                        } catch (Throwable th2) {
                            mp0Var.a();
                            throw th2;
                        }
                    }
                    throw th;
                } catch (Throwable th3) {
                    a();
                    throw th3;
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:117:0x01a6  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x008e A[Catch: all -> 0x003e, TRY_LEAVE, TryCatch #7 {all -> 0x003e, blocks: (B:3:0x0013, B:5:0x0035, B:7:0x0039, B:11:0x0047, B:12:0x004b, B:16:0x0056, B:29:0x0081, B:31:0x008e, B:148:0x0043), top: B:2:0x0013 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void e(defpackage.cd r34) {
        /*
            Method dump skipped, instructions count: 489
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.yh.e(cd):void");
    }

    public final void f() {
        synchronized (this.h) {
            try {
                cd cdVar = this.p;
                cdVar.getClass();
                if (!cdVar.w.F()) {
                    e(this.p);
                }
            } catch (Throwable th) {
                try {
                    if (!this.i.e.g()) {
                        mp0 mp0Var = this.y;
                        try {
                            mp0Var.g(this.i, this.z.z());
                            mp0Var.b();
                            mp0Var.a();
                        } catch (Throwable th2) {
                            mp0Var.a();
                            throw th2;
                        }
                    }
                    throw th;
                } catch (Throwable th3) {
                    a();
                    throw th3;
                }
            }
        }
    }

    public final void g() {
        mp0 mp0Var;
        synchronized (this.h) {
            try {
                this.z.v = null;
                if (!this.i.e.g()) {
                    mp0Var = this.y;
                    try {
                        mp0Var.g(this.i, this.z.z());
                        mp0Var.b();
                        mp0Var.a();
                    } finally {
                    }
                }
            } catch (Throwable th) {
                try {
                    if (!this.i.e.g()) {
                        mp0Var = this.y;
                        try {
                            mp0Var.g(this.i, this.z.z());
                            mp0Var.b();
                            mp0Var.a();
                        } finally {
                        }
                    }
                    throw th;
                } catch (Throwable th2) {
                    a();
                    throw th2;
                }
            }
        }
    }

    public final void h() {
        long j;
        char c;
        long j2;
        long j3;
        long[] jArr;
        long[] jArr2;
        int i;
        int i2;
        long j4;
        char c2;
        long j5;
        long j6;
        int i3;
        boolean z;
        int i4;
        int i5;
        ve0 ve0Var = this.n;
        long[] jArr3 = ve0Var.a;
        int length = jArr3.length - 2;
        long j7 = 255;
        char c3 = 7;
        long j8 = -9187201950435737472L;
        int i6 = 8;
        if (length >= 0) {
            int i7 = 0;
            while (true) {
                long j9 = jArr3[i7];
                j3 = 128;
                if ((((~j9) << c3) & j9 & j8) != j8) {
                    int i8 = 8 - ((~(i7 - length)) >>> 31);
                    int i9 = 0;
                    while (i9 < i8) {
                        if ((j9 & j7) < 128) {
                            j4 = j7;
                            int i10 = (i7 << 3) + i9;
                            Object obj = ve0Var.b[i10];
                            Object obj2 = ve0Var.c[i10];
                            c2 = c3;
                            boolean z2 = obj2 instanceof we0;
                            j5 = j8;
                            ve0 ve0Var2 = this.k;
                            if (z2) {
                                we0 we0Var = (we0) obj2;
                                Object[] objArr = we0Var.b;
                                long[] jArr4 = we0Var.a;
                                int length2 = jArr4.length - 2;
                                if (length2 >= 0) {
                                    int i11 = i6;
                                    j6 = j9;
                                    int i12 = 0;
                                    while (true) {
                                        long j10 = jArr4[i12];
                                        jArr2 = jArr3;
                                        i = length;
                                        if ((((~j10) << c2) & j10 & j5) != j5) {
                                            int i13 = 8 - ((~(i12 - length2)) >>> 31);
                                            int i14 = 0;
                                            while (i14 < i13) {
                                                if ((j10 & j4) < 128) {
                                                    i4 = i14;
                                                    int i15 = (i12 << 3) + i4;
                                                    i5 = i9;
                                                    if (!ve0Var2.c((ym) objArr[i15])) {
                                                        we0Var.m(i15);
                                                    }
                                                } else {
                                                    i4 = i14;
                                                    i5 = i9;
                                                }
                                                j10 >>= i11;
                                                i14 = i4 + 1;
                                                i9 = i5;
                                            }
                                            i2 = i9;
                                            if (i13 != i11) {
                                                break;
                                            }
                                        } else {
                                            i2 = i9;
                                        }
                                        if (i12 == length2) {
                                            break;
                                        }
                                        i12++;
                                        jArr3 = jArr2;
                                        length = i;
                                        i9 = i2;
                                        i11 = 8;
                                    }
                                } else {
                                    jArr2 = jArr3;
                                    i = length;
                                    i2 = i9;
                                    j6 = j9;
                                }
                                z = we0Var.g();
                            } else {
                                jArr2 = jArr3;
                                i = length;
                                i2 = i9;
                                j6 = j9;
                                obj2.getClass();
                                if (!ve0Var2.c((ym) obj2)) {
                                    z = true;
                                } else {
                                    z = false;
                                }
                            }
                            if (z) {
                                ve0Var.l(i10);
                            }
                            i3 = 8;
                        } else {
                            jArr2 = jArr3;
                            i = length;
                            i2 = i9;
                            j4 = j7;
                            c2 = c3;
                            j5 = j8;
                            j6 = j9;
                            i3 = i6;
                        }
                        j9 = j6 >> i3;
                        i9 = i2 + 1;
                        i6 = i3;
                        c3 = c2;
                        j7 = j4;
                        j8 = j5;
                        jArr3 = jArr2;
                        length = i;
                    }
                    jArr = jArr3;
                    int i16 = length;
                    j = j7;
                    c = c3;
                    j2 = j8;
                    if (i8 != i6) {
                        break;
                    } else {
                        length = i16;
                    }
                } else {
                    jArr = jArr3;
                    j = j7;
                    c = c3;
                    j2 = j8;
                }
                if (i7 == length) {
                    break;
                }
                i7++;
                c3 = c;
                j7 = j;
                j8 = j2;
                jArr3 = jArr;
                i6 = 8;
            }
        } else {
            j = 255;
            c = 7;
            j2 = -9187201950435737472L;
            j3 = 128;
        }
        we0 we0Var2 = this.m;
        if (we0Var2.h()) {
            Object[] objArr2 = we0Var2.b;
            long[] jArr5 = we0Var2.a;
            int length3 = jArr5.length - 2;
            if (length3 >= 0) {
                int i17 = 0;
                while (true) {
                    long j11 = jArr5[i17];
                    if ((((~j11) << c) & j11 & j2) != j2) {
                        int i18 = 8 - ((~(i17 - length3)) >>> 31);
                        for (int i19 = 0; i19 < i18; i19++) {
                            if ((j11 & j) < j3) {
                                int i20 = (i17 << 3) + i19;
                                if (((mo0) objArr2[i20]).g == null) {
                                    we0Var2.m(i20);
                                }
                            }
                            j11 >>= 8;
                        }
                        if (i18 != 8) {
                            return;
                        }
                    }
                    if (i17 != length3) {
                        i17++;
                    } else {
                        return;
                    }
                }
            }
        }
    }

    public final boolean i() {
        boolean z;
        synchronized (this.h) {
            z = true;
            if (this.A != 1) {
                z = false;
            }
            if (z) {
                this.A = 0;
            }
        }
        return z;
    }

    public final void j(kv kvVar) {
        try {
            synchronized (this.h) {
                n();
                ve0 ve0Var = this.r;
                this.r = t20.n();
                try {
                    bw bwVar = this.z;
                    iw0 iw0Var = this.t;
                    if (!bwVar.e.w.F()) {
                        rh.a("Expected applyChanges() to have been called");
                    }
                    bwVar.P = iw0Var;
                    try {
                        bwVar.n(ve0Var, kvVar);
                    } finally {
                        bwVar.P = null;
                    }
                } catch (Throwable th) {
                    this.r = ve0Var;
                    throw th;
                }
            }
        } catch (Throwable th2) {
            try {
                if (!this.i.e.g()) {
                    mp0 mp0Var = this.y;
                    try {
                        mp0Var.g(this.i, this.z.z());
                        mp0Var.b();
                        mp0Var.a();
                    } catch (Throwable th3) {
                        mp0Var.a();
                        throw th3;
                    }
                }
                throw th2;
            } catch (Throwable th4) {
                a();
                throw th4;
            }
        }
    }

    public final hl0 k(boolean z, kv kvVar) {
        if (this.u != null) {
            cn0.b("A pausable composition is in progress");
        }
        hl0 hl0Var = new hl0(this, this.e, this.z, this.i, kvVar, z, this.f, this.h);
        this.u = hl0Var;
        return hl0Var;
    }

    public final void l() {
        boolean z;
        mp0 mp0Var;
        synchronized (this.h) {
            try {
                if (this.u != null) {
                    cn0.b("Deactivate is not supported while pausable composition is in progress");
                }
                if (this.j.f == 0) {
                    z = true;
                } else {
                    z = false;
                }
                try {
                    try {
                        if (z) {
                            if (!this.i.e.g()) {
                            }
                            this.k.a();
                            this.n.a();
                            this.r.a();
                            this.o.w.D();
                            this.p.w.D();
                            bw bwVar = this.z;
                            bwVar.E.clear();
                            bwVar.s.clear();
                            bwVar.e.w.D();
                            bwVar.v = null;
                            this.A = 1;
                        }
                        mp0Var.g(this.i, this.z.z());
                        if (!z) {
                            rw0 rw0Var = this.j;
                            mp0 mp0Var2 = this.y;
                            uw0 d = rw0Var.d();
                            try {
                                d.n(d.t, new eb(2, mp0Var2, d));
                                d.e(true);
                                this.f.e();
                                mp0Var.c();
                            } catch (Throwable th) {
                                d.e(false);
                                throw th;
                            }
                        }
                        mp0Var.b();
                        mp0Var.a();
                        this.k.a();
                        this.n.a();
                        this.r.a();
                        this.o.w.D();
                        this.p.w.D();
                        bw bwVar2 = this.z;
                        bwVar2.E.clear();
                        bwVar2.s.clear();
                        bwVar2.e.w.D();
                        bwVar2.v = null;
                        this.A = 1;
                    } catch (Throwable th2) {
                        mp0Var.a();
                        throw th2;
                    }
                    mp0Var = this.y;
                } finally {
                    Trace.endSection();
                }
                Trace.beginSection("Compose:deactivate");
            } catch (Throwable th3) {
                throw th3;
            }
        }
    }

    public final void m() {
        boolean z;
        synchronized (this.h) {
            try {
                if (this.z.F) {
                    cn0.b("Composition is disposed while composing. If dispose is triggered by a call in @Composable function, consider wrapping it with SideEffect block.");
                }
                if (this.A != 3) {
                    this.A = 3;
                    cd cdVar = this.z.L;
                    if (cdVar != null) {
                        e(cdVar);
                    }
                    int i = 1;
                    if (this.j.f == 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (!z || !this.i.e.g()) {
                        mp0 mp0Var = this.y;
                        try {
                            mp0Var.g(this.i, this.z.z());
                            if (!z) {
                                rw0 rw0Var = this.j;
                                mp0 mp0Var2 = this.y;
                                uw0 d = rw0Var.d();
                                try {
                                    d.n(d.t, new wa(i, mp0Var2));
                                    d.H();
                                    d.e(true);
                                    this.f.l();
                                    this.f.e();
                                    mp0Var.c();
                                } catch (Throwable th) {
                                    d.e(false);
                                    throw th;
                                }
                            }
                            mp0Var.b();
                            mp0Var.a();
                        } catch (Throwable th2) {
                            mp0Var.a();
                            throw th2;
                        }
                    }
                    bw bwVar = this.z;
                    bwVar.getClass();
                    Trace.beginSection("Compose:Composer.dispose");
                    try {
                        bwVar.b.u(bwVar);
                        bwVar.E.clear();
                        bwVar.s.clear();
                        bwVar.e.w.D();
                        bwVar.v = null;
                        bwVar.a.l();
                        Trace.endSection();
                    } catch (Throwable th3) {
                        Trace.endSection();
                        throw th3;
                    }
                }
            } catch (Throwable th4) {
                throw th4;
            }
        }
        this.e.v(this);
    }

    public final void n() {
        Object obj = f31.a;
        AtomicReference atomicReference = this.g;
        Object andSet = atomicReference.getAndSet(obj);
        if (andSet != null) {
            if (!andSet.equals(obj)) {
                if (andSet instanceof Set) {
                    c((Set) andSet, true);
                    return;
                }
                if (andSet instanceof Object[]) {
                    for (Set set : (Set[]) andSet) {
                        c(set, true);
                    }
                    return;
                }
                rh.b("corrupt pendingModifications drain: " + atomicReference);
                throw new RuntimeException();
            }
            rh.b("pending composition has not been applied");
            throw new RuntimeException();
        }
    }

    public final void o() {
        AtomicReference atomicReference = this.g;
        Object andSet = atomicReference.getAndSet(null);
        if (!o20.e(andSet, f31.a)) {
            if (andSet instanceof Set) {
                c((Set) andSet, false);
                return;
            }
            if (andSet instanceof Object[]) {
                for (Set set : (Set[]) andSet) {
                    c(set, false);
                }
                return;
            }
            if (andSet == null) {
                if (this.u == null) {
                    rh.a("calling recordModificationsOf and applyChanges concurrently is not supported");
                }
            } else {
                rh.b("corrupt pendingModifications drain: " + atomicReference);
                throw new RuntimeException();
            }
        }
    }

    public final void p() {
        ir irVar = ir.e;
        AtomicReference atomicReference = this.g;
        Object andSet = atomicReference.getAndSet(irVar);
        if (!o20.e(andSet, f31.a) && andSet != null) {
            if (andSet instanceof Set) {
                c((Set) andSet, false);
                return;
            }
            if (andSet instanceof Object[]) {
                for (Set set : (Set[]) andSet) {
                    c(set, false);
                }
                return;
            }
            rh.b("corrupt pendingModifications drain: " + atomicReference);
            throw new RuntimeException();
        }
    }

    public final void q() {
        String str;
        int i = this.A;
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        str = "";
                    } else {
                        str = "The composition is disposed";
                    }
                } else {
                    str = "A previous pausable composition for this composition was cancelled. This composition must be disposed.";
                }
            } else {
                str = "The composition should be activated before setting content.";
            }
            cn0.b(str);
        }
        if (this.u == null) {
            return;
        }
        cn0.b("A pausable composition is in progress");
    }

    public final void r(ArrayList arrayList) {
        ye0 ye0Var = this.i;
        bw bwVar = this.z;
        if (arrayList.size() > 0) {
            ((wd0) ((xj0) arrayList.get(0)).e).getClass();
            rh.a("Check failed");
        }
        try {
            bwVar.getClass();
            Trace.beginSection("Compose:insertMovableContent");
            try {
                try {
                    bwVar.B(arrayList);
                    bwVar.i();
                } catch (Throwable th) {
                    bwVar.a();
                    throw th;
                }
            } finally {
                Trace.endSection();
            }
        } catch (Throwable th2) {
            try {
                if (!ye0Var.e.g()) {
                    mp0 mp0Var = this.y;
                    try {
                        mp0Var.g(ye0Var, bwVar.z());
                        mp0Var.b();
                        mp0Var.a();
                    } catch (Throwable th3) {
                        mp0Var.a();
                        throw th3;
                    }
                }
                throw th2;
            } catch (Throwable th4) {
                a();
                throw th4;
            }
        }
    }

    public final w20 s(mo0 mo0Var, Object obj) {
        yh yhVar;
        int i = mo0Var.b;
        if ((i & 2) != 0) {
            mo0Var.b = i | 4;
        }
        wv wvVar = mo0Var.c;
        if (wvVar != null && wvVar.a()) {
            rw0 rw0Var = this.j;
            rw0Var.getClass();
            wv wvVar2 = mo0Var.c;
            if (wvVar2 != null && rw0Var.e(k81.i(wvVar2))) {
                if (mo0Var.d != null) {
                    w20 t = t(mo0Var, wvVar, obj);
                    if (t != w20.e) {
                        this.x.g();
                    }
                    return t;
                }
                return w20.e;
            }
            synchronized (this.h) {
                yhVar = this.v;
            }
            if (yhVar != null) {
                bw bwVar = yhVar.z;
                if (bwVar.F && bwVar.a0(mo0Var, obj)) {
                    return w20.h;
                }
            }
            return w20.e;
        }
        return w20.e;
    }

    public final w20 t(mo0 mo0Var, wv wvVar, Object obj) {
        boolean z;
        synchronized (this.h) {
            try {
                yh yhVar = this.v;
                yh yhVar2 = null;
                if (yhVar != null) {
                    rw0 rw0Var = this.j;
                    int i = this.w;
                    if (rw0Var.k) {
                        rh.a("Writer is active");
                    }
                    if (i < 0 || i >= rw0Var.f) {
                        rh.a("Invalid group index");
                    }
                    wv i2 = k81.i(wvVar);
                    if (rw0Var.e(i2)) {
                        int i3 = rw0Var.e[(i * 5) + 3] + i;
                        int i4 = i2.a;
                        if (i <= i4 && i4 < i3) {
                            yhVar2 = yhVar;
                        }
                    }
                    yhVar = null;
                    yhVar2 = yhVar;
                }
                if (yhVar2 == null) {
                    bw bwVar = this.z;
                    if (bwVar.F && bwVar.a0(mo0Var, obj)) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (z) {
                        return w20.h;
                    }
                    if (obj == null) {
                        this.r.m(mo0Var, dt0.f);
                    } else {
                        boolean z2 = obj instanceof ym;
                        ve0 ve0Var = this.r;
                        if (!z2) {
                            ve0Var.m(mo0Var, dt0.f);
                        } else {
                            Object g = ve0Var.g(mo0Var);
                            if (g != null) {
                                if (g instanceof we0) {
                                    we0 we0Var = (we0) g;
                                    Object[] objArr = we0Var.b;
                                    long[] jArr = we0Var.a;
                                    int length = jArr.length - 2;
                                    if (length >= 0) {
                                        int i5 = 0;
                                        loop0: while (true) {
                                            long j = jArr[i5];
                                            if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                                                int i6 = 8 - ((~(i5 - length)) >>> 31);
                                                for (int i7 = 0; i7 < i6; i7++) {
                                                    if ((255 & j) < 128 && objArr[(i5 << 3) + i7] == dt0.f) {
                                                        break loop0;
                                                    }
                                                    j >>= 8;
                                                }
                                                if (i6 != 8) {
                                                    break;
                                                }
                                            }
                                            if (i5 == length) {
                                                break;
                                            }
                                            i5++;
                                        }
                                    }
                                } else if (g == dt0.f) {
                                }
                            }
                            t20.g(this.r, mo0Var, obj);
                        }
                    }
                }
                if (yhVar2 != null) {
                    return yhVar2.t(mo0Var, wvVar, obj);
                }
                this.e.l(this);
                if (this.z.F) {
                    return w20.g;
                }
                return w20.f;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void u(Object obj) {
        Object g = this.k.g(obj);
        if (g != null) {
            boolean z = g instanceof we0;
            w20 w20Var = w20.h;
            ve0 ve0Var = this.q;
            if (z) {
                we0 we0Var = (we0) g;
                Object[] objArr = we0Var.b;
                long[] jArr = we0Var.a;
                int length = jArr.length - 2;
                if (length >= 0) {
                    int i = 0;
                    while (true) {
                        long j = jArr[i];
                        if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                            int i2 = 8 - ((~(i - length)) >>> 31);
                            for (int i3 = 0; i3 < i2; i3++) {
                                if ((255 & j) < 128) {
                                    mo0 mo0Var = (mo0) objArr[(i << 3) + i3];
                                    if (mo0Var.b(obj) == w20Var) {
                                        t20.g(ve0Var, obj, mo0Var);
                                    }
                                }
                                j >>= 8;
                            }
                            if (i2 != 8) {
                                return;
                            }
                        }
                        if (i != length) {
                            i++;
                        } else {
                            return;
                        }
                    }
                }
            } else {
                mo0 mo0Var2 = (mo0) g;
                if (mo0Var2.b(obj) == w20Var) {
                    t20.g(ve0Var, obj, mo0Var2);
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0052, code lost:
    
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final boolean v(java.util.Set r18) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            boolean r2 = r1 instanceof defpackage.bt0
            ve0 r3 = r0.n
            ve0 r0 = r0.k
            r4 = 0
            r5 = 1
            if (r2 == 0) goto L5e
            bt0 r1 = (defpackage.bt0) r1
            we0 r1 = r1.e
            java.lang.Object[] r2 = r1.b
            long[] r1 = r1.a
            int r6 = r1.length
            int r6 = r6 + (-2)
            if (r6 < 0) goto L7b
            r7 = r4
        L1c:
            r8 = r1[r7]
            long r10 = ~r8
            r12 = 7
            long r10 = r10 << r12
            long r10 = r10 & r8
            r12 = -9187201950435737472(0x8080808080808080, double:-2.937446524422997E-306)
            long r10 = r10 & r12
            int r10 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r10 == 0) goto L59
            int r10 = r7 - r6
            int r10 = ~r10
            int r10 = r10 >>> 31
            r11 = 8
            int r10 = 8 - r10
            r12 = r4
        L36:
            if (r12 >= r10) goto L57
            r13 = 255(0xff, double:1.26E-321)
            long r13 = r13 & r8
            r15 = 128(0x80, double:6.3E-322)
            int r13 = (r13 > r15 ? 1 : (r13 == r15 ? 0 : -1))
            if (r13 >= 0) goto L53
            int r13 = r7 << 3
            int r13 = r13 + r12
            r13 = r2[r13]
            boolean r14 = r0.c(r13)
            if (r14 != 0) goto L52
            boolean r13 = r3.c(r13)
            if (r13 == 0) goto L53
        L52:
            return r5
        L53:
            long r8 = r8 >> r11
            int r12 = r12 + 1
            goto L36
        L57:
            if (r10 != r11) goto L7b
        L59:
            if (r7 == r6) goto L7b
            int r7 = r7 + 1
            goto L1c
        L5e:
            java.lang.Iterable r1 = (java.lang.Iterable) r1
            java.util.Iterator r1 = r1.iterator()
        L64:
            boolean r2 = r1.hasNext()
            if (r2 == 0) goto L7b
            java.lang.Object r2 = r1.next()
            boolean r6 = r0.c(r2)
            if (r6 != 0) goto L7a
            boolean r2 = r3.c(r2)
            if (r2 == 0) goto L64
        L7a:
            return r5
        L7b:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.yh.v(java.util.Set):boolean");
    }

    public final boolean w() {
        synchronized (this.h) {
            hl0 hl0Var = this.u;
            boolean z = false;
            if (hl0Var != null && (hl0Var.h.get() != jl0.i || hl0Var.i != m20.o())) {
                AtomicReference atomicReference = hl0Var.h;
                jl0 jl0Var = jl0.j;
                jl0 jl0Var2 = jl0.h;
                while (!atomicReference.compareAndSet(jl0Var, jl0Var2) && atomicReference.get() == jl0Var) {
                }
                ((ge0) hl0Var.l.f).a(9);
                return false;
            }
            n();
            try {
                ve0 ve0Var = this.r;
                this.r = t20.n();
                try {
                    bw bwVar = this.z;
                    iw0 iw0Var = this.t;
                    bj0 bj0Var = bwVar.e.w;
                    if (!bj0Var.F()) {
                        rh.a("Expected applyChanges() to have been called");
                    }
                    if (ve0Var.e > 0 || !bwVar.s.isEmpty()) {
                        bwVar.P = iw0Var;
                        try {
                            bwVar.n(ve0Var, null);
                            bwVar.P = null;
                            z = !bj0Var.F();
                        } catch (Throwable th) {
                            bwVar.P = null;
                            throw th;
                        }
                    }
                    if (!z) {
                        o();
                    }
                    return z;
                } catch (Throwable th2) {
                    this.r = ve0Var;
                    throw th2;
                }
            } catch (Throwable th3) {
                try {
                    if (!this.i.e.g()) {
                        mp0 mp0Var = this.y;
                        try {
                            mp0Var.g(this.i, this.z.z());
                            mp0Var.b();
                            mp0Var.a();
                        } catch (Throwable th4) {
                            mp0Var.a();
                            throw th4;
                        }
                    }
                    throw th3;
                } catch (Throwable th5) {
                    a();
                    throw th5;
                }
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v11, types: [java.util.Set[]] */
    /* JADX WARN: Type inference failed for: r1v9, types: [java.lang.Object[]] */
    public final void x(bt0 bt0Var) {
        bt0 bt0Var2;
        while (true) {
            Object obj = this.g.get();
            if (obj != null && !obj.equals(f31.a)) {
                if (obj instanceof Set) {
                    bt0Var2 = new Set[]{obj, bt0Var};
                } else if (obj instanceof Object[]) {
                    Set[] setArr = (Set[]) obj;
                    int length = setArr.length;
                    ?? copyOf = Arrays.copyOf(setArr, length + 1);
                    copyOf[length] = bt0Var;
                    bt0Var2 = copyOf;
                } else {
                    throw new IllegalStateException(("corrupt pendingModifications: " + this.g).toString());
                }
            } else {
                bt0Var2 = bt0Var;
            }
            AtomicReference atomicReference = this.g;
            while (!atomicReference.compareAndSet(obj, bt0Var2)) {
                if (atomicReference.get() != obj) {
                    break;
                }
            }
            if (obj == null) {
                synchronized (this.h) {
                    o();
                }
                return;
            }
            return;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:46:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final void y(java.lang.Object r21) {
        /*
            Method dump skipped, instructions count: 217
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.yh.y(java.lang.Object):void");
    }

    public final void z(Object obj) {
        synchronized (this.h) {
            try {
                u(obj);
                Object g = this.n.g(obj);
                if (g != null) {
                    if (g instanceof we0) {
                        we0 we0Var = (we0) g;
                        Object[] objArr = we0Var.b;
                        long[] jArr = we0Var.a;
                        int length = jArr.length - 2;
                        if (length >= 0) {
                            int i = 0;
                            while (true) {
                                long j = jArr[i];
                                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                                    int i2 = 8 - ((~(i - length)) >>> 31);
                                    for (int i3 = 0; i3 < i2; i3++) {
                                        if ((255 & j) < 128) {
                                            u((ym) objArr[(i << 3) + i3]);
                                        }
                                        j >>= 8;
                                    }
                                    if (i2 != 8) {
                                        break;
                                    }
                                }
                                if (i == length) {
                                    break;
                                } else {
                                    i++;
                                }
                            }
                        }
                    } else {
                        u((ym) g);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
