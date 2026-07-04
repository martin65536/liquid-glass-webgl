package defpackage;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.view.KeyEvent;
import com.kyant.backdrop.catalog.R;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class n20 {
    public static final float[] a = new float[91];
    public static final wq b = new wq("UNDEFINED", 1);
    public static final wq c = new wq("REUSABLE_CLAIMED", 1);
    public static final pz d = new pz(false);
    public static final int[] e = {1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000};
    public static final b6 f = new b6(5);
    public static final byte[] g = {48, 49, 53, 0};
    public static final byte[] h = {48, 49, 48, 0};
    public static final byte[] i = {48, 48, 57, 0};
    public static final byte[] j = {48, 48, 53, 0};
    public static final byte[] k = {48, 48, 49, 0};
    public static final byte[] l = {48, 48, 49, 0};
    public static final byte[] m = {48, 48, 50, 0};
    public static final ts0 n = new ts0(26);
    public static final qt0 o = new Object();
    public static final hn p = new hn(1);
    public static final rt0 q = new Object();
    public static final Object r = new Object();
    public static final ey0 s = new ey0(13);

    public static final int A(h8 h8Var, Object obj, int i2) {
        int i3 = h8Var.g;
        if (i3 == 0) {
            return -1;
        }
        try {
            int m2 = o4.m(h8Var.e, i3, i2);
            if (m2 < 0 || o20.e(obj, h8Var.f[m2])) {
                return m2;
            }
            int i4 = m2 + 1;
            while (i4 < i3 && h8Var.e[i4] == i2) {
                if (o20.e(obj, h8Var.f[i4])) {
                    return i4;
                }
                i4++;
            }
            for (int i5 = m2 - 1; i5 >= 0 && h8Var.e[i5] == i2; i5--) {
                if (o20.e(obj, h8Var.f[i5])) {
                    return i5;
                }
            }
            return ~i4;
        } catch (IndexOutOfBoundsException unused) {
            throw new ConcurrentModificationException();
        }
    }

    public static final boolean B(pt ptVar) {
        z40 z40Var;
        ng0 ng0Var;
        z40 z40Var2;
        ng0 ng0Var2 = ptVar.l;
        if (ng0Var2 != null && (z40Var = ng0Var2.s) != null && z40Var.F() && (ng0Var = ptVar.l) != null && (z40Var2 = ng0Var.s) != null && z40Var2.E()) {
            return true;
        }
        return false;
    }

    public static final boolean C(KeyEvent keyEvent) {
        long a2 = y20.a(keyEvent.getKeyCode());
        if (!v30.a(a2, v30.h) && !v30.a(a2, v30.k) && !v30.a(a2, v30.o) && !v30.a(a2, v30.j)) {
            return false;
        }
        return true;
    }

    public static final boolean D(bw bwVar) {
        if ((((Configuration) bwVar.j(p4.a)).uiMode & 48) == 32) {
            return true;
        }
        return false;
    }

    public static final int[] E(int i2, ArrayList arrayList) {
        int i3;
        int i4 = 0;
        if (Build.VERSION.SDK_INT >= 26) {
            int size = arrayList.size();
            int[] iArr = new int[size];
            while (i4 < size) {
                iArr[i4] = f31.P(((se) arrayList.get(i4)).a);
                i4++;
            }
            return iArr;
        }
        int[] iArr2 = new int[arrayList.size() + i2];
        int size2 = arrayList.size() - 1;
        int size3 = arrayList.size();
        int i5 = 0;
        while (i4 < size3) {
            long j2 = ((se) arrayList.get(i4)).a;
            if (se.d(j2) == 0.0f) {
                if (i4 == 0) {
                    i3 = i5 + 1;
                    iArr2[i5] = f31.P(se.b(((se) arrayList.get(1)).a, 0.0f));
                } else if (i4 == size2) {
                    i3 = i5 + 1;
                    iArr2[i5] = f31.P(se.b(((se) arrayList.get(i4 - 1)).a, 0.0f));
                } else {
                    int i6 = i5 + 1;
                    iArr2[i5] = f31.P(se.b(((se) arrayList.get(i4 - 1)).a, 0.0f));
                    i5 += 2;
                    iArr2[i6] = f31.P(se.b(((se) arrayList.get(i4 + 1)).a, 0.0f));
                }
                i5 = i3;
            } else {
                iArr2[i5] = f31.P(j2);
                i5++;
            }
            i4++;
        }
        return iArr2;
    }

    public static final float[] F(ArrayList arrayList, ArrayList arrayList2, int i2) {
        int i3 = 0;
        if (i2 == 0) {
            float[] fArr = new float[arrayList.size()];
            int size = arrayList.size();
            int i4 = 0;
            while (i4 < size) {
                Object obj = arrayList.get(i4);
                i4++;
                fArr[i3] = ((Number) obj).floatValue();
                i3++;
            }
            return fArr;
        }
        float[] fArr2 = new float[arrayList2.size() + i2];
        fArr2[0] = ((Number) arrayList.get(0)).floatValue();
        int size2 = arrayList2.size() - 1;
        int i5 = 1;
        for (int i6 = 1; i6 < size2; i6++) {
            long j2 = ((se) arrayList2.get(i6)).a;
            float floatValue = ((Number) arrayList.get(i6)).floatValue();
            int i7 = i5 + 1;
            fArr2[i5] = floatValue;
            if (se.d(j2) == 0.0f) {
                i5 += 2;
                fArr2[i7] = floatValue;
            } else {
                i5 = i7;
            }
        }
        fArr2[i5] = ((Number) arrayList.get(arrayList2.size() - 1)).floatValue();
        return fArr2;
    }

    public static cd0 G(cd0 cd0Var, uj0 uj0Var, dt0 dt0Var, float f2, te teVar, int i2) {
        ba baVar = x1.k;
        if ((i2 & 8) != 0) {
            dt0Var = dj.b;
        }
        dt0 dt0Var2 = dt0Var;
        if ((i2 & 16) != 0) {
            f2 = 1.0f;
        }
        return cd0Var.b(new vj0(uj0Var, baVar, dt0Var2, f2, teVar));
    }

    public static final long H(c00 c00Var, dj0 dj0Var, b00 b00Var, boolean z) {
        float intBitsToFloat;
        long floatToRawIntBits;
        long j2;
        long j3 = c00Var.g;
        if (dj0Var != null) {
            int i2 = b00Var.a;
            if (i2 == 1) {
                intBitsToFloat = Float.intBitsToFloat((int) (j3 >> 32));
            } else if (i2 == 2) {
                intBitsToFloat = Float.intBitsToFloat((int) (j3 & 4294967295L));
            }
            if (dj0Var == dj0.f) {
                long floatToRawIntBits2 = Float.floatToRawIntBits(intBitsToFloat);
                floatToRawIntBits = Float.floatToRawIntBits(0.0f);
                j2 = floatToRawIntBits2 << 32;
            } else {
                long floatToRawIntBits3 = Float.floatToRawIntBits(0.0f);
                floatToRawIntBits = Float.floatToRawIntBits(intBitsToFloat);
                j2 = floatToRawIntBits3 << 32;
            }
            j3 = j2 | (4294967295L & floatToRawIntBits);
        }
        long f2 = ch0.f(I(c00Var, dj0Var, b00Var), j3);
        if (!z && c00Var.i) {
            return 0L;
        }
        return f2;
    }

    public static final long I(c00 c00Var, dj0 dj0Var, b00 b00Var) {
        float intBitsToFloat;
        long floatToRawIntBits;
        long j2;
        if (dj0Var == null) {
            return c00Var.c;
        }
        int i2 = b00Var.a;
        if (i2 == 1) {
            intBitsToFloat = Float.intBitsToFloat((int) (c00Var.c >> 32));
        } else if (i2 == 2) {
            intBitsToFloat = Float.intBitsToFloat((int) (c00Var.c & 4294967295L));
        } else {
            return c00Var.c;
        }
        if (dj0Var == dj0.f) {
            long floatToRawIntBits2 = Float.floatToRawIntBits(intBitsToFloat);
            floatToRawIntBits = Float.floatToRawIntBits(0.0f);
            j2 = floatToRawIntBits2 << 32;
        } else {
            long floatToRawIntBits3 = Float.floatToRawIntBits(0.0f);
            floatToRawIntBits = Float.floatToRawIntBits(intBitsToFloat);
            j2 = floatToRawIntBits3 << 32;
        }
        return j2 | (4294967295L & floatToRawIntBits);
    }

    public static byte[] J(InputStream inputStream, int i2) {
        byte[] bArr = new byte[i2];
        int i3 = 0;
        while (i3 < i2) {
            int read = inputStream.read(bArr, i3, i2 - i3);
            if (read >= 0) {
                i3 += read;
            } else {
                throw new IllegalStateException("Not enough bytes to read: " + i2);
            }
        }
        return bArr;
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x005d, code lost:
    
        if (r0.finished() == false) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0062, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x006a, code lost:
    
        throw new java.lang.IllegalStateException("Inflater did not finish");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static byte[] K(java.io.FileInputStream r8, int r9, int r10) {
        /*
            java.util.zip.Inflater r0 = new java.util.zip.Inflater
            r0.<init>()
            byte[] r1 = new byte[r10]     // Catch: java.lang.Throwable -> L2e
            r2 = 2048(0x800, float:2.87E-42)
            byte[] r2 = new byte[r2]     // Catch: java.lang.Throwable -> L2e
            r3 = 0
            r4 = r3
            r5 = r4
        Le:
            boolean r6 = r0.finished()     // Catch: java.lang.Throwable -> L2e
            if (r6 != 0) goto L57
            boolean r6 = r0.needsDictionary()     // Catch: java.lang.Throwable -> L2e
            if (r6 != 0) goto L57
            if (r4 >= r9) goto L57
            int r6 = r8.read(r2)     // Catch: java.lang.Throwable -> L2e
            if (r6 < 0) goto L3b
            r0.setInput(r2, r3, r6)     // Catch: java.lang.Throwable -> L2e
            int r7 = r10 - r5
            int r7 = r0.inflate(r1, r5, r7)     // Catch: java.lang.Throwable -> L2e java.util.zip.DataFormatException -> L30
            int r5 = r5 + r7
            int r4 = r4 + r6
            goto Le
        L2e:
            r8 = move-exception
            goto L8a
        L30:
            r8 = move-exception
            java.lang.String r8 = r8.getMessage()     // Catch: java.lang.Throwable -> L2e
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L2e
            r9.<init>(r8)     // Catch: java.lang.Throwable -> L2e
            throw r9     // Catch: java.lang.Throwable -> L2e
        L3b:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L2e
            r8.<init>()     // Catch: java.lang.Throwable -> L2e
            java.lang.String r10 = "Invalid zip data. Stream ended after $totalBytesRead bytes. Expected "
            r8.append(r10)     // Catch: java.lang.Throwable -> L2e
            r8.append(r9)     // Catch: java.lang.Throwable -> L2e
            java.lang.String r9 = " bytes"
            r8.append(r9)     // Catch: java.lang.Throwable -> L2e
            java.lang.String r8 = r8.toString()     // Catch: java.lang.Throwable -> L2e
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L2e
            r9.<init>(r8)     // Catch: java.lang.Throwable -> L2e
            throw r9     // Catch: java.lang.Throwable -> L2e
        L57:
            if (r4 != r9) goto L6b
            boolean r8 = r0.finished()     // Catch: java.lang.Throwable -> L2e
            if (r8 == 0) goto L63
            r0.end()
            return r1
        L63:
            java.lang.String r8 = "Inflater did not finish"
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L2e
            r9.<init>(r8)     // Catch: java.lang.Throwable -> L2e
            throw r9     // Catch: java.lang.Throwable -> L2e
        L6b:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L2e
            r8.<init>()     // Catch: java.lang.Throwable -> L2e
            java.lang.String r10 = "Didn't read enough bytes during decompression. expected="
            r8.append(r10)     // Catch: java.lang.Throwable -> L2e
            r8.append(r9)     // Catch: java.lang.Throwable -> L2e
            java.lang.String r9 = " actual="
            r8.append(r9)     // Catch: java.lang.Throwable -> L2e
            r8.append(r4)     // Catch: java.lang.Throwable -> L2e
            java.lang.String r8 = r8.toString()     // Catch: java.lang.Throwable -> L2e
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> L2e
            r9.<init>(r8)     // Catch: java.lang.Throwable -> L2e
            throw r9     // Catch: java.lang.Throwable -> L2e
        L8a:
            r0.end()
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.n20.K(java.io.FileInputStream, int, int):byte[]");
    }

    public static long L(InputStream inputStream, int i2) {
        byte[] J = J(inputStream, i2);
        long j2 = 0;
        for (int i3 = 0; i3 < i2; i3++) {
            j2 += (J[i3] & 255) << (i3 * 8);
        }
        return j2;
    }

    public static final vc M(gv gvVar, bw bwVar) {
        gvVar.getClass();
        boolean f2 = bwVar.f(gvVar);
        Object L = bwVar.L();
        if (f2 || L == ph.a) {
            L = new vc(gvVar);
            bwVar.f0(L);
        }
        return (vc) L;
    }

    public static final void N(ij ijVar, Object obj) {
        Object qfVar;
        v31 v31Var;
        if (ijVar instanceof in) {
            in inVar = (in) ijVar;
            ak akVar = inVar.h;
            jj jjVar = inVar.i;
            Throwable a2 = kq0.a(obj);
            if (a2 == null) {
                qfVar = obj;
            } else {
                qfVar = new qf(a2, false);
            }
            if (akVar.k(jjVar.r())) {
                inVar.j = qfVar;
                inVar.g = 1;
                akVar.g(jjVar.r(), inVar);
                return;
            }
            nr a3 = w11.a();
            if (a3.g >= 4294967296L) {
                inVar.j = qfVar;
                inVar.g = 1;
                a3.r(inVar);
                return;
            }
            a3.u(true);
            try {
                d30 d30Var = (d30) jjVar.r().j(x1.L);
                if (d30Var != null && !d30Var.b()) {
                    inVar.u(o30.l(d30Var.m()));
                } else {
                    Object obj2 = inVar.k;
                    yj r2 = jjVar.r();
                    Object Q = k81.Q(r2, obj2);
                    if (Q != k81.o) {
                        v31Var = f31.W(jjVar, r2, Q);
                    } else {
                        v31Var = null;
                    }
                    try {
                        jjVar.u(obj);
                    } finally {
                        if (v31Var == null || v31Var.p0()) {
                            k81.G(r2, Q);
                        }
                    }
                }
                do {
                } while (a3.x());
            } finally {
                try {
                    return;
                } finally {
                }
            }
            return;
        }
        ijVar.u(obj);
    }

    public static final void O(ArrayList arrayList, ArrayList arrayList2) {
        if (arrayList.size() == arrayList2.size()) {
            return;
        }
        v7.m("colors and colorStops arguments must have equal length.");
    }

    public static void P(ByteArrayOutputStream byteArrayOutputStream, long j2, int i2) {
        byte[] bArr = new byte[i2];
        for (int i3 = 0; i3 < i2; i3++) {
            bArr[i3] = (byte) ((j2 >> (i3 * 8)) & 255);
        }
        byteArrayOutputStream.write(bArr);
    }

    public static void Q(ByteArrayOutputStream byteArrayOutputStream, int i2) {
        P(byteArrayOutputStream, i2, 2);
    }

    public static final void a(cd0 cd0Var, ba baVar, gg ggVar, bw bwVar, int i2) {
        int i3;
        boolean z;
        int i4;
        int i5;
        int i6;
        bwVar.W(380139498);
        if ((i2 & 6) == 0) {
            if (bwVar.f(cd0Var)) {
                i6 = 4;
            } else {
                i6 = 2;
            }
            i3 = i6 | i2;
        } else {
            i3 = i2;
        }
        if ((i2 & 48) == 0) {
            if (bwVar.f(baVar)) {
                i5 = 32;
            } else {
                i5 = 16;
            }
            i3 |= i5;
        }
        int i7 = i3 | 384;
        if ((i2 & 3072) == 0) {
            if (bwVar.h(ggVar)) {
                i4 = 2048;
            } else {
                i4 = 1024;
            }
            i7 |= i4;
        }
        int i8 = 0;
        boolean z2 = true;
        if ((i7 & 1171) != 1170) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i7 & 1, z)) {
            pc0 d2 = ya.d(baVar);
            if ((i7 & 7168) != 2048) {
                z2 = false;
            }
            boolean f2 = bwVar.f(d2) | z2;
            Object L = bwVar.L();
            if (f2 || L == ph.a) {
                L = new eb(i8, d2, ggVar);
                bwVar.f0(L);
            }
            jc0.d(cd0Var, (kv) L, bwVar, i7 & 14);
        } else {
            bwVar.R();
        }
        mo0 r2 = bwVar.r();
        if (r2 != null) {
            r2.d = new fb(cd0Var, baVar, ggVar, i2, 0);
        }
    }

    public static final void b(bw bwVar, int i2) {
        boolean z;
        boolean z2;
        long f2;
        long j2;
        cd0 cd0Var;
        bwVar.W(-1341102188);
        if (i2 != 0) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i2 & 1, z)) {
            if (!D(bwVar)) {
                f2 = f31.f(4278225151L);
            } else {
                f2 = f31.f(4278227455L);
            }
            long j3 = f2;
            long j4 = se.b;
            long b2 = se.b(j4, 0.05f);
            long b3 = se.b(j4, 0.4f);
            jr0 jr0Var = new jr0(34.0f);
            ad adVar = new ad();
            long j5 = se.c;
            long b4 = se.b(j5, 0.2f);
            q41 E = n30.E(f31.C(), bwVar);
            da daVar = new da(5, j5);
            Object L = bwVar.L();
            Object obj = ph.a;
            if (L == obj) {
                L = dl.r(bwVar);
                bwVar.f0(L);
            }
            hk hkVar = (hk) L;
            Object L2 = bwVar.L();
            if (L2 == obj) {
                L2 = dl.a(1.0f, 0.01f);
                bwVar.f0(L2);
            }
            y6 y6Var = (y6) L2;
            Object L3 = bwVar.L();
            if (L3 == obj) {
                L3 = dl.a(1.0f, 0.01f);
                bwVar.f0(L3);
            }
            y6 y6Var2 = (y6) L3;
            Object L4 = bwVar.L();
            int i3 = 4;
            if (L4 == obj) {
                L4 = n30.r(new n9(i3, y6Var));
                bwVar.f0(L4);
            }
            hy0 hy0Var = (hy0) L4;
            Context context = (Context) bwVar.j(p4.b);
            Object L5 = bwVar.L();
            if (L5 == obj) {
                L5 = new s31(context);
                bwVar.f0(L5);
            }
            Object obj2 = (s31) L5;
            boolean h2 = bwVar.h(obj2);
            Object L6 = bwVar.L();
            if (!h2 && L6 != obj) {
                j2 = b4;
            } else {
                j2 = b4;
                L6 = new q2(25, obj2);
                bwVar.f0(L6);
            }
            dl.f(x31.a, (gv) L6, bwVar);
            Object L7 = bwVar.L();
            if (L7 == obj) {
                L7 = new n9(3, jr0Var);
                bwVar.f0(L7);
            }
            vu vuVar = (vu) L7;
            boolean h3 = bwVar.h(obj2);
            Object L8 = bwVar.L();
            int i4 = 2;
            if (h3 || L8 == obj) {
                L8 = new n9(i4, obj2);
                bwVar.f0(L8);
            }
            vu vuVar2 = (vu) L8;
            boolean h4 = bwVar.h(y6Var2);
            Object L9 = bwVar.L();
            if (h4 || L9 == obj) {
                L9 = new o6(4, y6Var2, hy0Var);
                bwVar.f0(L9);
            }
            gv gvVar = (gv) L9;
            Object L10 = bwVar.L();
            if (L10 == obj) {
                L10 = new tj(0, b2);
                bwVar.f0(L10);
            }
            gv gvVar2 = (gv) L10;
            boolean h5 = bwVar.h(y6Var2);
            Object L11 = bwVar.L();
            if (h5 || L11 == obj) {
                L11 = new o2(y6Var2, 2);
                bwVar.f0(L11);
            }
            gv gvVar3 = (gv) L11;
            Object L12 = bwVar.L();
            if (L12 == obj) {
                L12 = new uj(hy0Var, 1);
                bwVar.f0(L12);
            }
            zc0 zc0Var = zc0.a;
            cd0 M = o4.M(zc0Var, (lv) L12);
            Object L13 = bwVar.L();
            if (L13 == obj) {
                cd0Var = M;
                L13 = new uj(hy0Var, 0);
                bwVar.f0(L13);
            } else {
                cd0Var = M;
            }
            cd0 M2 = o4.M(zc0Var, (lv) L13);
            boolean h6 = bwVar.h(y6Var) | bwVar.h(hkVar) | bwVar.h(y6Var2);
            Object L14 = bwVar.L();
            if (h6 || L14 == obj) {
                L14 = new oj(y6Var, hkVar, y6Var2);
                bwVar.f0(L14);
            }
            hp hpVar = ip.a;
            af0 D = n30.D((gv) L14, bwVar);
            Object L15 = bwVar.L();
            if (L15 == obj) {
                Object mlVar = new ml(new ep(D, 1));
                bwVar.f0(mlVar);
                L15 = mlVar;
            }
            ml mlVar2 = (ml) L15;
            boolean h7 = bwVar.h(y6Var) | bwVar.h(hkVar) | bwVar.h(y6Var2);
            Object L16 = bwVar.L();
            if (h7 || L16 == obj) {
                L16 = new rj(y6Var, hkVar, y6Var2, null);
                bwVar.f0(L16);
            }
            gp gpVar = new gp(mlVar2, ip.a, (lv) L16);
            boolean h8 = bwVar.h(y6Var2);
            Object L17 = bwVar.L();
            if (h8 || L17 == obj) {
                L17 = new sj(y6Var2, b3, 0);
                bwVar.f0(L17);
            }
            cd0 z3 = o4.z(gpVar, (gv) L17);
            boolean h9 = bwVar.h(y6Var2);
            Object L18 = bwVar.L();
            if (h9 || L18 == obj) {
                L18 = new o2(y6Var2, 1);
                bwVar.f0(L18);
            }
            z2 = false;
            f31.b(k81.w(z3, (gv) L18), jc0.C(-1207488151, new lj(cd0Var, vuVar, gvVar3, vuVar2, gvVar, gvVar2, adVar, j2, E, daVar, j3, M2), bwVar), bwVar, 48, 0);
        } else {
            z2 = false;
            bwVar.R();
        }
        mo0 r2 = bwVar.r();
        if (r2 != null) {
            r2.d = new yu0(i2, 6, z2);
        }
    }

    public static final void c(bw bwVar, int i2) {
        boolean z;
        long j2;
        long f2;
        long b2;
        long b3;
        bwVar.W(-1148309392);
        boolean z2 = false;
        int i3 = 1;
        if (i2 != 0) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i2 & 1, z)) {
            boolean D = D(bwVar);
            boolean z3 = !D;
            if (!D) {
                j2 = se.b;
            } else {
                j2 = se.c;
            }
            long j3 = j2;
            if (!D) {
                f2 = f31.f(4278225151L);
            } else {
                f2 = f31.f(4278227455L);
            }
            long j4 = f2;
            if (!D) {
                b2 = se.b(f31.f(4294638330L), 0.6f);
            } else {
                b2 = se.b(f31.f(4279374354L), 0.4f);
            }
            if (!D) {
                b3 = se.b(f31.f(4280887610L), 0.23f);
            } else {
                b3 = se.b(f31.f(4279374354L), 0.56f);
            }
            boolean e2 = bwVar.e(b3);
            Object L = bwVar.L();
            if (e2 || L == ph.a) {
                L = new tj(i3, b3);
                bwVar.f0(L);
            }
            f31.b(o4.z(zc0.a, (gv) L), jc0.C(-1958763771, new en(z3, b2, j3, j4), bwVar), bwVar, 48, 0);
        } else {
            bwVar.R();
        }
        mo0 r2 = bwVar.r();
        if (r2 != null) {
            r2.d = new yu0(i2, 7, z2);
        }
    }

    public static final void d(u41 u41Var, c00 c00Var, dj0 dj0Var, b00 b00Var, pu puVar, long j2) {
        float intBitsToFloat;
        long floatToRawIntBits;
        pe0 pe0Var = (pe0) puVar.b;
        float intBitsToFloat2 = Float.intBitsToFloat((int) (c00Var.c >> 32));
        float intBitsToFloat3 = Float.intBitsToFloat((int) (c00Var.c & 4294967295L));
        if (i(c00Var)) {
            puVar.a = 0;
            pe0Var.d();
        }
        if (!e(c00Var) && !i(c00Var)) {
            if (pe0Var.b == 3) {
                int i2 = puVar.a;
                puVar.a = i2 + 1;
                pe0Var.n(i2, c00Var);
            } else {
                pe0Var.a(c00Var);
            }
            if (puVar.a == 3) {
                puVar.a = 0;
            }
            Object[] objArr = pe0Var.a;
            int i3 = pe0Var.b;
            float f2 = 0.0f;
            for (int i4 = 0; i4 < i3; i4++) {
                f2 += Float.intBitsToFloat((int) (((c00) objArr[i4]).c >> 32));
            }
            int i5 = pe0Var.b;
            intBitsToFloat2 = f2 / i5;
            Object[] objArr2 = pe0Var.a;
            float f3 = 0.0f;
            for (int i6 = 0; i6 < i5; i6++) {
                f3 += Float.intBitsToFloat((int) (((c00) objArr2[i6]).c & 4294967295L));
            }
            intBitsToFloat3 = f3 / pe0Var.b;
        }
        long floatToRawIntBits2 = (Float.floatToRawIntBits(intBitsToFloat2) << 32) | (Float.floatToRawIntBits(intBitsToFloat3) & 4294967295L);
        if (dj0Var != null) {
            int i7 = b00Var.a;
            if (i7 == 1) {
                intBitsToFloat = Float.intBitsToFloat((int) (floatToRawIntBits2 >> 32));
            } else if (i7 == 2) {
                intBitsToFloat = Float.intBitsToFloat((int) (floatToRawIntBits2 & 4294967295L));
            }
            if (dj0Var == dj0.f) {
                floatToRawIntBits = (Float.floatToRawIntBits(intBitsToFloat) << 32) | (Float.floatToRawIntBits(0.0f) & 4294967295L);
            } else {
                floatToRawIntBits = (Float.floatToRawIntBits(intBitsToFloat) & 4294967295L) | (Float.floatToRawIntBits(0.0f) << 32);
            }
            floatToRawIntBits2 = floatToRawIntBits;
        }
        ((fm) u41Var.a).a(c00Var.b, ch0.g(floatToRawIntBits2, j2));
    }

    public static final boolean e(c00 c00Var) {
        if (c00Var.h && !c00Var.d) {
            return true;
        }
        return false;
    }

    public static final boolean f(su0 su0Var) {
        nu0 k2 = su0Var.k();
        return !k2.e.c(wu0.j);
    }

    public static final boolean g(su0 su0Var, Resources resources) {
        boolean z;
        Object g2 = su0Var.d.e.g(wu0.a);
        String str = null;
        if (g2 == null) {
            g2 = null;
        }
        List list = (List) g2;
        if (list != null) {
            str = (String) me.T(list);
        }
        if (str == null && y(su0Var) == null && x(su0Var, resources) == null && !w(su0Var)) {
            z = false;
        } else {
            z = true;
        }
        if (!o20.u(su0Var) && (su0Var.d.g || (su0Var.q() && z))) {
            return true;
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0032  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x001f  */
    /* JADX WARN: Type inference failed for: r7v0, types: [bp0, java.lang.Object] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final java.lang.Object h(defpackage.hu0 r10, long r11, defpackage.jj r13) {
        /*
            boolean r0 = r13 instanceof defpackage.st0
            if (r0 == 0) goto L13
            r0 = r13
            st0 r0 = (defpackage.st0) r0
            int r1 = r0.k
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.k = r1
            goto L18
        L13:
            st0 r0 = new st0
            r0.<init>(r13)
        L18:
            java.lang.Object r13 = r0.j
            int r1 = r0.k
            r2 = 1
            if (r1 == 0) goto L32
            if (r1 != r2) goto L2b
            bp0 r10 = r0.i
            hu0 r11 = r0.h
            defpackage.o30.x(r13)
            r7 = r10
            r10 = r11
            goto L55
        L2b:
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            defpackage.v7.o(r10)
            r10 = 0
            return r10
        L32:
            defpackage.o30.x(r13)
            bp0 r7 = new bp0
            r7.<init>()
            g r3 = new g
            r8 = 0
            r9 = 1
            r4 = r10
            r5 = r11
            r3.<init>(r4, r5, r7, r8, r9)
            r0.h = r4
            r0.i = r7
            r0.k = r2
            gf0 r10 = defpackage.gf0.e
            java.lang.Object r10 = r4.f(r10, r3, r0)
            ik r11 = defpackage.ik.e
            if (r10 != r11) goto L54
            return r11
        L54:
            r10 = r4
        L55:
            float r11 = r7.e
            long r10 = r10.h(r11)
            ch0 r12 = new ch0
            r12.<init>(r10)
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.n20.h(hu0, long, jj):java.lang.Object");
    }

    public static final boolean i(c00 c00Var) {
        if (!c00Var.h && c00Var.d) {
            return true;
        }
        return false;
    }

    public static cd0 j(cd0 cd0Var, a00 a00Var, cr0 cr0Var, vu vuVar) {
        cd0 ohVar;
        if (a00Var != null) {
            ohVar = new be(null, a00Var, false, cr0Var, vuVar);
        } else if (a00Var == null) {
            ohVar = new be(null, null, false, cr0Var, vuVar);
        } else {
            ohVar = new oh(new ce(a00Var, cr0Var, vuVar));
        }
        return cd0Var.b(ohVar);
    }

    public static cd0 k(cd0 cd0Var, vu vuVar) {
        return cd0Var.b(new be(null, null, true, null, vuVar));
    }

    public static final cd0 l(cd0 cd0Var, zv0 zv0Var) {
        return k81.x(cd0Var, zv0Var, null, 518143);
    }

    public static int m(Comparable comparable, Comparable comparable2) {
        if (comparable == comparable2) {
            return 0;
        }
        if (comparable == null) {
            return -1;
        }
        if (comparable2 == null) {
            return 1;
        }
        return comparable.compareTo(comparable2);
    }

    public static byte[] n(byte[] bArr) {
        Deflater deflater = new Deflater(1);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            DeflaterOutputStream deflaterOutputStream = new DeflaterOutputStream(byteArrayOutputStream, deflater);
            try {
                deflaterOutputStream.write(bArr);
                deflaterOutputStream.close();
                deflater.end();
                return byteArrayOutputStream.toByteArray();
            } finally {
            }
        } catch (Throwable th) {
            deflater.end();
            throw th;
        }
    }

    public static final int o(ArrayList arrayList) {
        int i2 = 0;
        if (Build.VERSION.SDK_INT >= 26) {
            return 0;
        }
        int size = arrayList.size() - 1;
        for (int i3 = 1; i3 < size; i3++) {
            if (se.d(((se) arrayList.get(i3)).a) == 0.0f) {
                i2++;
            }
        }
        return i2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final Object p(ai aiVar, do0 do0Var) {
        if (!((bd0) aiVar).e.r) {
            q00.b("Cannot read CompositionLocal because the Modifier node is not currently attached.");
        }
        ll0 ll0Var = (ll0) k81.E(aiVar).D;
        ll0Var.getClass();
        return jc0.A(ll0Var, do0Var);
    }

    public static boolean q(File file) {
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            if (listFiles == null) {
                return false;
            }
            boolean z = true;
            for (File file2 : listFiles) {
                if (q(file2) && z) {
                    z = true;
                } else {
                    z = false;
                }
            }
            return z;
        }
        file.delete();
        return true;
    }

    public static final void r(up upVar, hx hxVar) {
        hxVar.c(upVar.J().q(), (hx) upVar.J().g);
    }

    public static final pt s(pt ptVar) {
        pt f2 = ((lt) ((b4) k81.F(ptVar)).getFocusOwner()).f();
        if (f2 != null && f2.r) {
            return f2;
        }
        return null;
    }

    public static final wo0 t(pt ptVar) {
        ng0 ng0Var;
        if (ptVar.r && (ng0Var = ptVar.l) != null) {
            l40 n2 = o30.n(ng0Var);
            if (!n2.Q()) {
                n2 = null;
            }
            if (n2 != null) {
                return ptVar.G0(n2);
            }
        }
        return wo0.e;
    }

    public static final void u(StringBuilder sb, StringBuilder sb2, int i2) {
        if (i2 < 10) {
            sb.append('0');
        }
        sb2.append(i2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:72:0x0026, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final defpackage.pt v(defpackage.pt r8) {
        /*
            bd0 r0 = r8.e
            boolean r0 = r0.r
            r1 = 0
            if (r0 != 0) goto L9
            goto Laa
        L9:
            if (r0 != 0) goto L10
            java.lang.String r0 = "visitChildren called on an unattached node"
            defpackage.q00.b(r0)
        L10:
            ef0 r0 = new ef0
            r2 = 16
            bd0[] r3 = new defpackage.bd0[r2]
            r0.<init>(r3)
            bd0 r8 = r8.e
            bd0 r3 = r8.j
            if (r3 != 0) goto L23
            defpackage.k81.f(r0, r8)
            goto L26
        L23:
            r0.b(r3)
        L26:
            int r8 = r0.g
            if (r8 == 0) goto Laa
            int r8 = r8 + (-1)
            java.lang.Object r8 = r0.k(r8)
            bd0 r8 = (defpackage.bd0) r8
            int r3 = r8.h
            r3 = r3 & 1024(0x400, float:1.435E-42)
            if (r3 != 0) goto L3c
            defpackage.k81.f(r0, r8)
            goto L26
        L3c:
            if (r8 == 0) goto L26
            int r3 = r8.g
            r3 = r3 & 1024(0x400, float:1.435E-42)
            if (r3 == 0) goto La7
            r3 = r1
        L45:
            if (r8 == 0) goto L26
            boolean r4 = r8 instanceof defpackage.pt
            r5 = 1
            if (r4 == 0) goto L6c
            pt r8 = (defpackage.pt) r8
            bd0 r4 = r8.e
            boolean r4 = r4.r
            if (r4 == 0) goto La2
            ot r4 = r8.I0()
            int r4 = r4.ordinal()
            if (r4 == 0) goto L6b
            if (r4 == r5) goto L6b
            r5 = 2
            if (r4 == r5) goto L6b
            r8 = 3
            if (r4 != r8) goto L67
            goto La2
        L67:
            defpackage.v7.k()
            return r1
        L6b:
            return r8
        L6c:
            int r4 = r8.g
            r4 = r4 & 1024(0x400, float:1.435E-42)
            if (r4 == 0) goto La2
            boolean r4 = r8 instanceof defpackage.jm
            if (r4 == 0) goto La2
            r4 = r8
            jm r4 = (defpackage.jm) r4
            bd0 r4 = r4.t
            r6 = 0
        L7c:
            if (r4 == 0) goto L9f
            int r7 = r4.g
            r7 = r7 & 1024(0x400, float:1.435E-42)
            if (r7 == 0) goto L9c
            int r6 = r6 + 1
            if (r6 != r5) goto L8a
            r8 = r4
            goto L9c
        L8a:
            if (r3 != 0) goto L93
            ef0 r3 = new ef0
            bd0[] r7 = new defpackage.bd0[r2]
            r3.<init>(r7)
        L93:
            if (r8 == 0) goto L99
            r3.b(r8)
            r8 = r1
        L99:
            r3.b(r4)
        L9c:
            bd0 r4 = r4.j
            goto L7c
        L9f:
            if (r6 != r5) goto La2
            goto L45
        La2:
            bd0 r8 = defpackage.k81.h(r3)
            goto L45
        La7:
            bd0 r8 = r8.j
            goto L3c
        Laa:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.n20.v(pt):pt");
    }

    public static final boolean w(su0 su0Var) {
        boolean z;
        Object g2 = su0Var.d.e.g(wu0.G);
        Object obj = null;
        if (g2 == null) {
            g2 = null;
        }
        h21 h21Var = (h21) g2;
        ve0 ve0Var = su0Var.d.e;
        Object g3 = ve0Var.g(wu0.w);
        if (g3 == null) {
            g3 = null;
        }
        cr0 cr0Var = (cr0) g3;
        if (h21Var != null) {
            z = true;
        } else {
            z = false;
        }
        Object g4 = ve0Var.g(wu0.F);
        if (g4 != null) {
            obj = g4;
        }
        if (((Boolean) obj) != null && (cr0Var == null || cr0Var.a != 4)) {
            return true;
        }
        return z;
    }

    public static final String x(su0 su0Var, Resources resources) {
        float f2;
        int j2;
        nu0 nu0Var = su0Var.d;
        nu0 nu0Var2 = su0Var.d;
        Object g2 = nu0Var.e.g(wu0.b);
        String str = null;
        if (g2 == null) {
            g2 = null;
        }
        ve0 ve0Var = nu0Var2.e;
        Object g3 = ve0Var.g(wu0.G);
        if (g3 == null) {
            g3 = null;
        }
        h21 h21Var = (h21) g3;
        Object g4 = ve0Var.g(wu0.w);
        if (g4 == null) {
            g4 = null;
        }
        cr0 cr0Var = (cr0) g4;
        if (h21Var != null) {
            int ordinal = h21Var.ordinal();
            if (ordinal != 0) {
                if (ordinal != 1) {
                    if (ordinal == 2) {
                        if (g2 == null) {
                            g2 = resources.getString(R.string.indeterminate);
                        }
                    } else {
                        v7.k();
                        return null;
                    }
                } else if (cr0Var != null && cr0Var.a == 2 && g2 == null) {
                    g2 = resources.getString(R.string.state_off);
                }
            } else if (cr0Var != null && cr0Var.a == 2 && g2 == null) {
                g2 = resources.getString(R.string.state_on);
            }
        }
        Object g5 = ve0Var.g(wu0.F);
        if (g5 == null) {
            g5 = null;
        }
        Boolean bool = (Boolean) g5;
        if (bool != null) {
            boolean booleanValue = bool.booleanValue();
            if ((cr0Var == null || cr0Var.a != 4) && g2 == null) {
                if (booleanValue) {
                    g2 = resources.getString(R.string.selected);
                } else {
                    g2 = resources.getString(R.string.not_selected);
                }
            }
        }
        Object g6 = ve0Var.g(wu0.c);
        if (g6 == null) {
            g6 = null;
        }
        ao0 ao0Var = (ao0) g6;
        if (ao0Var != null) {
            if (ao0Var != ao0.b) {
                if (g2 == null) {
                    float f3 = ao0Var.a.e;
                    if (f3 - 0.0f == 0.0f) {
                        f2 = 0.0f;
                    } else {
                        f2 = 0.0f / (f3 - 0.0f);
                    }
                    if (f2 < 0.0f) {
                        f2 = 0.0f;
                    }
                    if (f2 > 1.0f) {
                        f2 = 1.0f;
                    }
                    if (f2 == 0.0f) {
                        j2 = 0;
                    } else if (f2 == 1.0f) {
                        j2 = 100;
                    } else {
                        j2 = n30.j(Math.round(f2 * 100.0f), 1, 99);
                    }
                    g2 = resources.getString(R.string.template_percent, Integer.valueOf(j2));
                }
            } else if (g2 == null) {
                g2 = resources.getString(R.string.in_progress);
            }
        }
        av0 av0Var = wu0.D;
        if (ve0Var.c(av0Var)) {
            ve0 ve0Var2 = new su0(su0Var.a, true, su0Var.c, nu0Var2).k().e;
            Object g7 = ve0Var2.g(wu0.a);
            if (g7 == null) {
                g7 = null;
            }
            Collection collection = (Collection) g7;
            if (collection == null || collection.isEmpty()) {
                Object g8 = ve0Var2.g(wu0.z);
                if (g8 == null) {
                    g8 = null;
                }
                Collection collection2 = (Collection) g8;
                if (collection2 == null || collection2.isEmpty()) {
                    Object g9 = ve0Var2.g(av0Var);
                    if (g9 == null) {
                        g9 = null;
                    }
                    CharSequence charSequence = (CharSequence) g9;
                    if (charSequence == null || charSequence.length() == 0) {
                        str = resources.getString(R.string.state_empty);
                    }
                }
            }
            g2 = str;
        }
        return (String) g2;
    }

    public static final l7 y(su0 su0Var) {
        Object g2 = su0Var.d.e.g(wu0.D);
        l7 l7Var = null;
        if (g2 == null) {
            g2 = null;
        }
        l7 l7Var2 = (l7) g2;
        Object g3 = su0Var.d.e.g(wu0.z);
        if (g3 == null) {
            g3 = null;
        }
        List list = (List) g3;
        if (list != null) {
            l7Var = (l7) me.T(list);
        }
        if (l7Var2 == null) {
            return l7Var;
        }
        return l7Var2;
    }

    public static String z(a2 a2Var) {
        if (a2Var instanceof z1) {
            return "image/*";
        }
        if (a2Var instanceof y1) {
            return null;
        }
        v7.k();
        return null;
    }
}
