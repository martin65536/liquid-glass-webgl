package defpackage;

import android.os.Bundle;
import android.view.View;
import com.kyant.backdrop.catalog.R;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class o30 {
    public static final void a(vu vuVar, gv gvVar, m9 m9Var, cd0 cd0Var, bw bwVar, int i) {
        int i2;
        boolean z;
        long f;
        long b;
        boolean z2;
        boolean z3;
        float f2;
        int i3;
        long j;
        Object obj;
        long j2;
        boolean z4;
        float f3;
        int i4;
        int i5;
        int i6;
        int i7;
        vuVar.getClass();
        gvVar.getClass();
        m9Var.getClass();
        bwVar.W(114260259);
        if ((i & 6) == 0) {
            if (bwVar.h(vuVar)) {
                i7 = 4;
            } else {
                i7 = 2;
            }
            i2 = i7 | i;
        } else {
            i2 = i;
        }
        if ((i & 48) == 0) {
            if (bwVar.h(gvVar)) {
                i6 = 32;
            } else {
                i6 = 16;
            }
            i2 |= i6;
        }
        if ((i & 384) == 0) {
            if (bwVar.h(m9Var)) {
                i5 = 256;
            } else {
                i5 = 128;
            }
            i2 |= i5;
        }
        if ((i & 3072) == 0) {
            if (bwVar.f(cd0Var)) {
                i4 = 2048;
            } else {
                i4 = 1024;
            }
            i2 |= i4;
        }
        int i8 = i2;
        if ((i8 & 1171) != 1170) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i8 & 1, z)) {
            boolean D = n20.D(bwVar);
            if (!D) {
                f = f31.f(4281648985L);
            } else {
                f = f31.f(4281389400L);
            }
            long j3 = f;
            if (!D) {
                b = se.b(f31.f(4286085240L), 0.2f);
            } else {
                b = se.b(f31.f(4286085248L), 0.36f);
            }
            long j4 = b;
            mm mmVar = (mm) bwVar.j(fi.h);
            if (bwVar.j(fi.n) == m40.e) {
                z2 = true;
            } else {
                z2 = false;
            }
            float G = mmVar.G(20.0f);
            Object L = bwVar.L();
            Object obj2 = ph.a;
            if (L == obj2) {
                L = dl.r(bwVar);
                bwVar.f0(L);
            }
            hk hkVar = (hk) L;
            Object L2 = bwVar.L();
            if (L2 == obj2) {
                L2 = n30.B(Boolean.FALSE);
                bwVar.f0(L2);
            }
            af0 af0Var = (af0) L2;
            Object L3 = bwVar.L();
            if (L3 == obj2) {
                if (((Boolean) vuVar.a()).booleanValue()) {
                    f3 = 1.0f;
                } else {
                    f3 = 0.0f;
                }
                Object ek0Var = new ek0(f3);
                bwVar.f0(ek0Var);
                L3 = ek0Var;
            }
            ek0 ek0Var2 = (ek0) L3;
            boolean f4 = bwVar.f(hkVar);
            Object L4 = bwVar.L();
            if (f4 || L4 == obj2) {
                L4 = new al(hkVar, ek0Var2.g(), new he(1.0f), 0.001f, 1.5f, kf.s, new nw(gvVar, vuVar, af0Var, ek0Var2), new ia0(G, z2, af0Var, ek0Var2));
                bwVar.f0(L4);
            }
            al alVar = (al) L4;
            boolean h = bwVar.h(alVar);
            Object L5 = bwVar.L();
            int i9 = 11;
            ij ijVar = null;
            if (h || L5 == obj2) {
                L5 = new d(ek0Var2, alVar, ijVar, i9);
                bwVar.f0(L5);
            }
            dl.i((kv) L5, bwVar, alVar);
            if ((i8 & 14) == 4) {
                z3 = true;
            } else {
                z3 = false;
            }
            boolean h2 = z3 | bwVar.h(alVar);
            Object L6 = bwVar.L();
            if (!h2 && L6 != obj2) {
                f2 = G;
                i3 = i8;
                j = j4;
                j2 = j3;
                obj = obj2;
            } else {
                f2 = G;
                i3 = i8;
                j = j4;
                obj = obj2;
                j2 = j3;
                Object fVar = new f(vuVar, alVar, ek0Var2, ijVar, 11);
                bwVar.f0(fVar);
                L6 = fVar;
            }
            dl.i((kv) L6, bwVar, vuVar);
            c40 E = jc0.E(bwVar);
            pc0 d = ya.d(x1.j);
            long j5 = bwVar.T;
            int i10 = (int) (j5 ^ (j5 >>> 32));
            ll0 l = bwVar.l();
            cd0 B = dl.B(bwVar, cd0Var);
            jh.c.getClass();
            vu vuVar2 = ih.b;
            bwVar.Y();
            if (bwVar.S) {
                bwVar.k(vuVar2);
            } else {
                bwVar.i0();
            }
            m20.F(ih.e, bwVar, d);
            m20.F(ih.d, bwVar, l);
            m20.F(ih.f, bwVar, Integer.valueOf(i10));
            m20.C(ih.g, bwVar);
            m20.F(ih.c, bwVar, B);
            zc0 zc0Var = zc0.a;
            cd0 l2 = n20.l(f31.H(zc0Var, E), new ad());
            boolean h3 = bwVar.h(alVar) | bwVar.e(j) | bwVar.e(j2);
            Object L7 = bwVar.L();
            if (h3 || L7 == obj) {
                z4 = z2;
                Object ga0Var = new ga0(alVar, j, j2);
                bwVar.f0(ga0Var);
                L7 = ga0Var;
            } else {
                z4 = z2;
            }
            ya.a(k81.K(o4.y(l2, (gv) L7), 64.0f, 28.0f), bwVar, 0);
            boolean h4 = bwVar.h(alVar) | bwVar.g(z4) | bwVar.c(f2);
            Object L8 = bwVar.L();
            if (h4 || L8 == obj) {
                L8 = new ha0(alVar, z4, f2);
                bwVar.f0(L8);
            }
            cd0 w = k81.w(zc0Var, (gv) L8);
            Object L9 = bwVar.L();
            if (L9 == obj) {
                L9 = w3.z;
                bwVar.f0(L9);
            }
            AtomicInteger atomicInteger = pu0.a;
            cd0 b2 = w.b(new s7((gv) L9)).b(alVar.s);
            boolean h5 = bwVar.h(alVar);
            Object L10 = bwVar.L();
            if (h5 || L10 == obj) {
                L10 = new aa0(alVar, 1);
                bwVar.f0(L10);
            }
            gf P = dl.P(m9Var, o4.W(E, (kv) L10, bwVar), bwVar, (i3 >> 6) & 14);
            Object L11 = bwVar.L();
            if (L11 == obj) {
                L11 = ba0.i;
                bwVar.f0(L11);
            }
            vu vuVar3 = (vu) L11;
            boolean h6 = bwVar.h(alVar);
            Object L12 = bwVar.L();
            if (h6 || L12 == obj) {
                L12 = new uk(alVar, 12);
                bwVar.f0(L12);
            }
            gv gvVar2 = (gv) L12;
            boolean h7 = bwVar.h(alVar);
            Object L13 = bwVar.L();
            if (h7 || L13 == obj) {
                L13 = new vk(alVar, 9);
                bwVar.f0(L13);
            }
            vu vuVar4 = (vu) L13;
            Object L14 = bwVar.L();
            if (L14 == obj) {
                L14 = ba0.j;
                bwVar.f0(L14);
            }
            vu vuVar5 = (vu) L14;
            boolean h8 = bwVar.h(alVar);
            Object L15 = bwVar.L();
            int i11 = 10;
            if (h8 || L15 == obj) {
                L15 = new vk(alVar, i11);
                bwVar.f0(L15);
            }
            vu vuVar6 = (vu) L15;
            boolean h9 = bwVar.h(alVar);
            Object L16 = bwVar.L();
            if (h9 || L16 == obj) {
                L16 = new uk(alVar, i11);
                bwVar.f0(L16);
            }
            gv gvVar3 = (gv) L16;
            boolean h10 = bwVar.h(alVar);
            Object L17 = bwVar.L();
            if (h10 || L17 == obj) {
                L17 = new uk(alVar, 11);
                bwVar.f0(L17);
            }
            ya.a(k81.K(f31.s(b2, P, vuVar3, gvVar2, vuVar4, vuVar5, vuVar6, gvVar3, null, null, (gv) L17, 2944), 40.0f, 24.0f), bwVar, 0);
            bwVar.p(true);
        } else {
            bwVar.R();
        }
        mo0 r = bwVar.r();
        if (r != null) {
            r.d = new g90(vuVar, gvVar, m9Var, cd0Var, i);
        }
    }

    public static final void b(c70 c70Var, Object obj, int i, Object obj2, bw bwVar, int i2) {
        int i3;
        int i4;
        int i5;
        int i6;
        boolean z;
        bwVar.W(1439843069);
        if (bwVar.f(c70Var)) {
            i3 = 4;
        } else {
            i3 = 2;
        }
        int i7 = i3 | i2;
        if (bwVar.f(obj)) {
            i4 = 32;
        } else {
            i4 = 16;
        }
        int i8 = i7 | i4;
        if (bwVar.d(i)) {
            i5 = 256;
        } else {
            i5 = 128;
        }
        int i9 = i8 | i5;
        if (bwVar.f(obj2)) {
            i6 = 2048;
        } else {
            i6 = 1024;
        }
        int i10 = i9 | i6;
        if ((i10 & 1171) != 1170) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i10 & 1, z)) {
            ((cs0) obj).b(obj2, jc0.C(980966366, new f60(i, c70Var, obj2), bwVar), bwVar, 48);
        } else {
            bwVar.R();
        }
        mo0 r = bwVar.r();
        if (r != null) {
            r.d = new fb(c70Var, obj, i, obj2, i2);
        }
    }

    public static final long c(float f, float f2) {
        return (Float.floatToRawIntBits(f2) & 4294967295L) | (Float.floatToRawIntBits(f) << 32);
    }

    public static void d(uw0 uw0Var, List list, yh yhVar) {
        Object obj;
        mo0 mo0Var;
        if (!list.isEmpty()) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                int c = uw0Var.c((wv) list.get(i));
                int N = uw0Var.N(uw0Var.b, uw0Var.r(c));
                if (N < uw0Var.g(uw0Var.b, uw0Var.r(c + 1))) {
                    obj = uw0Var.c[uw0Var.h(N)];
                } else {
                    obj = ph.a;
                }
                if (obj instanceof mo0) {
                    mo0Var = (mo0) obj;
                } else {
                    mo0Var = null;
                }
                if (mo0Var != null) {
                    mo0Var.a = yhVar;
                }
            }
        }
    }

    public static final float e(long j) {
        if (Float.intBitsToFloat((int) (j >> 32)) == 0.0f && Float.intBitsToFloat((int) (j & 4294967295L)) == 0.0f) {
            return 0.0f;
        }
        return ((-((float) Math.atan2(Float.intBitsToFloat(r0), Float.intBitsToFloat((int) (j & 4294967295L))))) * 180.0f) / 3.1415927f;
    }

    public static void f(StringBuilder sb, Object obj, gv gvVar) {
        boolean z;
        if (gvVar != null) {
            sb.append((CharSequence) gvVar.e(obj));
            return;
        }
        if (obj == null) {
            z = true;
        } else {
            z = obj instanceof CharSequence;
        }
        if (z) {
            sb.append((CharSequence) obj);
        } else if (obj instanceof Character) {
            sb.append(((Character) obj).charValue());
        } else {
            sb.append((CharSequence) obj.toString());
        }
    }

    public static final void g(s51 s51Var, c4 c4Var, l80 l80Var) {
        AutoCloseable autoCloseable;
        c4Var.getClass();
        l80Var.getClass();
        t51 t51Var = s51Var.a;
        if (t51Var != null) {
            synchronized (t51Var.a) {
                autoCloseable = (AutoCloseable) t51Var.b.get("androidx.lifecycle.savedstate.vm.tag");
            }
        } else {
            autoCloseable = null;
        }
        js0 js0Var = (js0) autoCloseable;
        if (js0Var != null && !js0Var.g) {
            js0Var.g(c4Var, l80Var);
            a80 a80Var = l80Var.c;
            if (a80Var != a80.f && a80Var.compareTo(a80.h) < 0) {
                l80Var.a(new am(c4Var, l80Var));
            } else {
                c4Var.v();
            }
        }
    }

    public static final wo0 h(l40 l40Var) {
        l40 C = l40Var.C();
        if (C != null) {
            return C.U(l40Var, true);
        }
        return new wo0(0.0f, 0.0f, (int) (l40Var.X() >> 32), (int) (l40Var.X() & 4294967295L));
    }

    public static final wo0 i(l40 l40Var, boolean z) {
        l40 n = n(l40Var);
        float X = (int) (n.X() >> 32);
        float X2 = (int) (n.X() & 4294967295L);
        wo0 U = n.U(l40Var, z);
        float f = U.a;
        float f2 = 0.0f;
        if (z) {
            if (f < 0.0f) {
                f = 0.0f;
            }
            if (f > X) {
                f = X;
            }
        }
        float f3 = U.b;
        if (z) {
            if (f3 < 0.0f) {
                f3 = 0.0f;
            }
            if (f3 > X2) {
                f3 = X2;
            }
        }
        float f4 = U.c;
        if (z) {
            if (f4 < 0.0f) {
                f4 = 0.0f;
            }
            if (f4 <= X) {
                X = f4;
            }
            f4 = X;
        }
        float f5 = U.d;
        if (z) {
            if (f5 >= 0.0f) {
                f2 = f5;
            }
            if (f2 <= X2) {
                X2 = f2;
            }
            f5 = X2;
        }
        if (f == f4 || f3 == f5) {
            return wo0.e;
        }
        long z2 = n.z((Float.floatToRawIntBits(f) << 32) | (Float.floatToRawIntBits(f3) & 4294967295L));
        long z3 = n.z((Float.floatToRawIntBits(f4) << 32) | (Float.floatToRawIntBits(f3) & 4294967295L));
        long z4 = n.z((Float.floatToRawIntBits(f4) << 32) | (Float.floatToRawIntBits(f5) & 4294967295L));
        long z5 = n.z((Float.floatToRawIntBits(f5) & 4294967295L) | (Float.floatToRawIntBits(f) << 32));
        float intBitsToFloat = Float.intBitsToFloat((int) (z2 >> 32));
        float intBitsToFloat2 = Float.intBitsToFloat((int) (z3 >> 32));
        float intBitsToFloat3 = Float.intBitsToFloat((int) (z5 >> 32));
        float intBitsToFloat4 = Float.intBitsToFloat((int) (z4 >> 32));
        float min = Math.min(intBitsToFloat, Math.min(intBitsToFloat2, Math.min(intBitsToFloat3, intBitsToFloat4)));
        float max = Math.max(intBitsToFloat, Math.max(intBitsToFloat2, Math.max(intBitsToFloat3, intBitsToFloat4)));
        float intBitsToFloat5 = Float.intBitsToFloat((int) (z2 & 4294967295L));
        float intBitsToFloat6 = Float.intBitsToFloat((int) (z3 & 4294967295L));
        float intBitsToFloat7 = Float.intBitsToFloat((int) (z5 & 4294967295L));
        float intBitsToFloat8 = Float.intBitsToFloat((int) (z4 & 4294967295L));
        return new wo0(min, Math.min(intBitsToFloat5, Math.min(intBitsToFloat6, Math.min(intBitsToFloat7, intBitsToFloat8))), max, Math.max(intBitsToFloat5, Math.max(intBitsToFloat6, Math.max(intBitsToFloat7, intBitsToFloat8))));
    }

    public static final long j(pm0 pm0Var, boolean z) {
        long j;
        List list = pm0Var.a;
        int size = list.size();
        long j2 = 0;
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            um0 um0Var = (um0) list.get(i2);
            if (um0Var.d && um0Var.h) {
                if (z) {
                    j = um0Var.c;
                } else {
                    j = um0Var.g;
                }
                j2 = ch0.g(j2, j);
                i++;
            }
        }
        if (i == 0) {
            return 9205357640488583168L;
        }
        return ch0.b(j2, i);
    }

    public static final float k(pm0 pm0Var, boolean z) {
        long j;
        long j2 = j(pm0Var, z);
        float f = 0.0f;
        if (ch0.c(j2, 9205357640488583168L)) {
            return 0.0f;
        }
        List list = pm0Var.a;
        int size = list.size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            um0 um0Var = (um0) list.get(i2);
            if (um0Var.d && um0Var.h) {
                if (z) {
                    j = um0Var.c;
                } else {
                    j = um0Var.g;
                }
                i++;
                f = ch0.d(ch0.f(j, j2)) + f;
            }
        }
        return f / i;
    }

    public static final jq0 l(Throwable th) {
        th.getClass();
        return new jq0(th);
    }

    public static final void m(uc ucVar, g30 g30Var, r5 r5Var) {
        if (g30Var instanceof gj0) {
            ucVar.i(((gj0) g30Var).a, r5Var);
            return;
        }
        if (g30Var instanceof hj0) {
            hj0 hj0Var = (hj0) g30Var;
            gr0 gr0Var = hj0Var.a;
            long j = gr0Var.h;
            y5 y5Var = hj0Var.b;
            if (y5Var != null) {
                ucVar.e(y5Var, r5Var);
                return;
            } else {
                ucVar.g(gr0Var.a, gr0Var.b, gr0Var.c, gr0Var.d, Float.intBitsToFloat((int) (j >> 32)), Float.intBitsToFloat((int) (j & 4294967295L)), r5Var);
                return;
            }
        }
        if (g30Var instanceof fj0) {
            ucVar.e(((fj0) g30Var).a, r5Var);
        } else {
            v7.k();
        }
    }

    public static final l40 n(l40 l40Var) {
        l40 l40Var2;
        ng0 ng0Var;
        l40 C = l40Var.C();
        while (true) {
            l40 l40Var3 = C;
            l40Var2 = l40Var;
            l40Var = l40Var3;
            if (l40Var == null) {
                break;
            }
            C = l40Var.C();
        }
        if (l40Var2 instanceof ng0) {
            ng0Var = (ng0) l40Var2;
        } else {
            ng0Var = null;
        }
        if (ng0Var == null) {
            return l40Var2;
        }
        ng0 ng0Var2 = ng0Var.u;
        while (true) {
            ng0 ng0Var3 = ng0Var2;
            ng0 ng0Var4 = ng0Var;
            ng0Var = ng0Var3;
            if (ng0Var != null) {
                ng0Var2 = ng0Var.u;
            } else {
                return ng0Var4;
            }
        }
    }

    public static final w51 o(View view) {
        w51 w51Var;
        while (view != null) {
            Object tag = view.getTag(R.id.view_tree_view_model_store_owner);
            if (tag instanceof w51) {
                w51Var = (w51) tag;
            } else {
                w51Var = null;
            }
            if (w51Var != null) {
                return w51Var;
            }
            Object j = y20.j(view);
            if (j instanceof View) {
                view = (View) j;
            } else {
                view = null;
            }
        }
        return null;
    }

    public static final long p(long j) {
        float intBitsToFloat = Float.intBitsToFloat((int) (j >> 32)) / 2.0f;
        float intBitsToFloat2 = Float.intBitsToFloat((int) (j & 4294967295L)) / 2.0f;
        return (Float.floatToRawIntBits(intBitsToFloat2) & 4294967295L) | (Float.floatToRawIntBits(intBitsToFloat) << 32);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to find 'out' block for switch in B:4:0x0015. Please report as an issue. */
    public static final Class q(wd wdVar) {
        wdVar.getClass();
        Class cls = wdVar.a;
        if (cls.isPrimitive()) {
            String name = cls.getName();
            switch (name.hashCode()) {
                case -1325958191:
                    if (name.equals("double")) {
                        return Double.class;
                    }
                    break;
                case 104431:
                    if (name.equals("int")) {
                        return Integer.class;
                    }
                    break;
                case 3039496:
                    if (name.equals("byte")) {
                        return Byte.class;
                    }
                    break;
                case 3052374:
                    if (name.equals("char")) {
                        return Character.class;
                    }
                    break;
                case 3327612:
                    if (name.equals("long")) {
                        return Long.class;
                    }
                    break;
                case 3625364:
                    if (name.equals("void")) {
                        return Void.class;
                    }
                    break;
                case 64711720:
                    if (name.equals("boolean")) {
                        return Boolean.class;
                    }
                    break;
                case 97526364:
                    if (name.equals("float")) {
                        return Float.class;
                    }
                    break;
                case 109413500:
                    if (name.equals("short")) {
                        return Short.class;
                    }
                    break;
            }
        }
        return cls;
    }

    public static final q6 r(yj yjVar) {
        q6 q6Var = (q6) yjVar.j(x1.P);
        if (q6Var != null) {
            return q6Var;
        }
        v7.o("A MonotonicFrameClock is not available in this CoroutineContext. Callers should supply an appropriate MonotonicFrameClock using withContext.");
        return null;
    }

    public static final bn0 s(View view) {
        bn0 bn0Var = (bn0) view.getTag(R.id.pooling_container_listener_holder_tag);
        if (bn0Var == null) {
            bn0 bn0Var2 = new bn0();
            view.setTag(R.id.pooling_container_listener_holder_tag, bn0Var2);
            return bn0Var2;
        }
        return bn0Var;
    }

    public static final Bundle t(Bundle bundle, String str) {
        Bundle bundle2 = bundle.getBundle(str);
        if (bundle2 != null) {
            return bundle2;
        }
        throw new IllegalArgumentException("No valid saved state was found for the key '" + str + "'. It may be missing, null, or not of the expected type. This can occur if the value was saved with a different type or if the saved state was modified unexpectedly.");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final void u(bd0 bd0Var, vu vuVar) {
        bh0 bh0Var = bd0Var.k;
        if (bh0Var == null) {
            bh0Var = new bh0((ah0) bd0Var);
            bd0Var.k = bh0Var;
        }
        pj0 snapshotObserver = ((b4) k81.F(bd0Var)).getSnapshotObserver();
        snapshotObserver.a.b(bh0Var, w3.E, vuVar);
    }

    public static final r11 v(r11 r11Var, m40 m40Var) {
        int i;
        int i2;
        float f;
        int i3;
        int i4;
        int i5;
        ux0 ux0Var = r11Var.a;
        a11 a11Var = vx0.d;
        a11 c = ux0Var.a.c(new ht0(2));
        long j = ux0Var.b;
        u11[] u11VarArr = t11.b;
        if ((j & 1095216660480L) == 0) {
            j = vx0.a;
        }
        long j2 = j;
        nu nuVar = ux0Var.c;
        if (nuVar == null) {
            nuVar = nu.g;
        }
        nu nuVar2 = nuVar;
        lu luVar = ux0Var.d;
        if (luVar != null) {
            i = luVar.a;
        } else {
            i = 0;
        }
        lu luVar2 = new lu(i);
        mu muVar = ux0Var.e;
        if (muVar != null) {
            i2 = muVar.a;
        } else {
            i2 = 65535;
        }
        mu muVar2 = new mu(i2);
        sl slVar = ux0Var.f;
        if (slVar == null) {
            slVar = sl.a;
        }
        sl slVar2 = slVar;
        String str = ux0Var.g;
        if (str == null) {
            str = "";
        }
        String str2 = str;
        long j3 = ux0Var.h;
        if ((j3 & 1095216660480L) == 0) {
            j3 = vx0.b;
        }
        long j4 = j3;
        t9 t9Var = ux0Var.i;
        float f2 = 0.0f;
        if (t9Var != null) {
            f = t9Var.a;
        } else {
            f = 0.0f;
        }
        if (!Float.isNaN(f)) {
            f2 = f;
        }
        t9 t9Var2 = new t9(f2);
        b11 b11Var = ux0Var.j;
        if (b11Var == null) {
            b11Var = b11.c;
        }
        b11 b11Var2 = b11Var;
        ua0 ua0Var = ux0Var.k;
        if (ua0Var == null) {
            ua0 ua0Var2 = ua0.g;
            ua0Var = km0.a.g();
        }
        ua0 ua0Var3 = ua0Var;
        long j5 = ux0Var.l;
        if (j5 == 16) {
            j5 = vx0.c;
        }
        long j6 = j5;
        w01 w01Var = ux0Var.m;
        if (w01Var == null) {
            w01Var = w01.b;
        }
        w01 w01Var2 = w01Var;
        tv0 tv0Var = ux0Var.n;
        if (tv0Var == null) {
            tv0Var = tv0.d;
        }
        tv0 tv0Var2 = tv0Var;
        jc0 jc0Var = ux0Var.o;
        if (jc0Var == null) {
            jc0Var = yr.s;
        }
        ux0 ux0Var2 = new ux0(c, j2, nuVar2, luVar2, muVar2, slVar2, str2, j4, t9Var2, b11Var2, ua0Var3, j6, w01Var2, tv0Var2, jc0Var);
        ck0 ck0Var = r11Var.b;
        int i6 = dk0.b;
        int i7 = ck0Var.a;
        int i8 = 5;
        if (i7 == 0) {
            i3 = 5;
        } else {
            i3 = i7;
        }
        int i9 = ck0Var.b;
        if (i9 == 3) {
            int ordinal = m40Var.ordinal();
            if (ordinal != 0) {
                if (ordinal != 1) {
                    v7.k();
                    return null;
                }
            } else {
                i8 = 4;
            }
            i4 = i8;
        } else if (i9 == 0) {
            int ordinal2 = m40Var.ordinal();
            if (ordinal2 != 0) {
                if (ordinal2 == 1) {
                    i4 = 2;
                } else {
                    v7.k();
                    return null;
                }
            } else {
                i4 = 1;
            }
        } else {
            i4 = i9;
        }
        long j7 = ck0Var.c;
        if ((j7 & 1095216660480L) == 0) {
            j7 = dk0.a;
        }
        c11 c11Var = ck0Var.d;
        if (c11Var == null) {
            c11Var = c11.c;
        }
        c11 c11Var2 = c11Var;
        mm0 mm0Var = ck0Var.e;
        x80 x80Var = ck0Var.f;
        int i10 = ck0Var.g;
        if (i10 == 0) {
            i10 = s80.b;
        }
        int i11 = i10;
        int i12 = ck0Var.h;
        if (i12 == 0) {
            i5 = 1;
        } else {
            i5 = i12;
        }
        l11 l11Var = ck0Var.i;
        if (l11Var == null) {
            l11Var = l11.c;
        }
        return new r11(ux0Var2, new ck0(i3, i4, j7, c11Var2, mm0Var, x80Var, i11, i5, l11Var), r11Var.c);
    }

    public static final Object w(ct0 ct0Var, ct0 ct0Var2, kv kvVar) {
        Object qfVar;
        Object W;
        try {
            f31.n(2, kvVar);
            qfVar = kvVar.d(ct0Var2, ct0Var);
        } catch (Throwable th) {
            qfVar = new qf(th, false);
        }
        ik ikVar = ik.e;
        if (qfVar != ikVar && (W = ct0Var.W(qfVar)) != o20.h) {
            if (!(W instanceof qf)) {
                return o20.K(W);
            }
            throw ((qf) W).a;
        }
        return ikVar;
    }

    public static final void x(Object obj) {
        if (!(obj instanceof jq0)) {
        } else {
            throw ((jq0) obj).e;
        }
    }

    public static final void y() {
        throw new UnsupportedOperationException();
    }
}
