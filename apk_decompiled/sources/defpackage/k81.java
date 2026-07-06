package defpackage;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.Trace;
import android.util.Size;
import android.util.SizeF;
import android.view.View;
import android.widget.EdgeEffect;
import com.kyant.backdrop.catalog.R;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class k81 {
    public static final wq a;
    public static pq0 c;
    public static final wq e;
    public static final c4 g;
    public static final c4 h;
    public static final c4 i;
    public static final c4 j;
    public static final c4 k;
    public static final wq o;
    public static final us0 p;
    public static final us0 q;
    public static final us0 r;
    public static final pm b = new pm(1.0f, 1.0f);
    public static final b6 d = new b6(3);
    public static final StackTraceElement[] f = new StackTraceElement[0];
    public static final zr l = new zr(gn.f, 1.0f);
    public static final zr m = new zr(gn.e, 1.0f);
    public static final zr n = new zr(gn.g, 1.0f);

    static {
        int i2 = 1;
        a = new wq("CLOSED", i2);
        e = new wq("NO_OWNER", i2);
        int i3 = 20;
        int i4 = 21;
        g = new c4(i4, new us0(14), new ts0(i3));
        h = new c4(i4, new us0(15), new ts0(i4));
        int i5 = 22;
        i = new c4(i4, new us0(16), new ts0(i5));
        j = new c4(i4, new us0(17), new ts0(23));
        k = new c4(i4, new us0(18), new ts0(24));
        o = new wq("NO_THREAD_ELEMENTS", i2);
        p = new us0(i3);
        q = new us0(i4);
        r = new us0(i5);
    }

    public static boolean A(char c2) {
        if (!Character.isWhitespace(c2) && !Character.isSpaceChar(c2)) {
            return false;
        }
        return true;
    }

    public static final ng0 B(im imVar, int i2) {
        ng0 ng0Var = ((bd0) imVar).e.l;
        ng0Var.getClass();
        if (ng0Var.P0() == imVar && og0.f(i2)) {
            ng0 ng0Var2 = ng0Var.t;
            ng0Var2.getClass();
            return ng0Var2;
        }
        return ng0Var;
    }

    public static final ex C(bd0 bd0Var) {
        return ((b4) F(bd0Var)).getGraphicsContext();
    }

    public static final ng0 D(im imVar) {
        if (!((bd0) imVar).e.r) {
            q00.b("Cannot get LayoutCoordinates, Modifier.Node is not attached.");
        }
        ng0 B = B(imVar, 2);
        if (!B.P0().r) {
            q00.b("LayoutCoordinates is not attached.");
        }
        return B;
    }

    public static final z40 E(im imVar) {
        ng0 ng0Var = ((bd0) imVar).e.l;
        if (ng0Var != null) {
            return ng0Var.s;
        }
        throw d3.t("Cannot obtain node coordinator. Is the Modifier.Node attached?");
    }

    public static final mj0 F(im imVar) {
        mj0 mj0Var = E(imVar).r;
        if (mj0Var != null) {
            return mj0Var;
        }
        throw d3.t("This node does not have an owner.");
    }

    public static final void G(yj yjVar, Object obj) {
        if (obj != o) {
            if (obj instanceof z11) {
                z11 z11Var = (z11) obj;
                m21[] m21VarArr = z11Var.c;
                int length = m21VarArr.length - 1;
                if (length < 0) {
                    return;
                }
                while (true) {
                    int i2 = length - 1;
                    m21VarArr[length].getClass();
                    Trace.endSection();
                    if (i2 >= 0) {
                        length = i2;
                    } else {
                        return;
                    }
                }
            } else {
                Object n2 = yjVar.n(q, null);
                n2.getClass();
                Trace.endSection();
            }
        }
    }

    public static final z10 H(wo0 wo0Var) {
        return new z10(Math.round(wo0Var.a), Math.round(wo0Var.b), Math.round(wo0Var.c), Math.round(wo0Var.d));
    }

    public static final void I(float[] fArr, Matrix matrix) {
        matrix.getValues(fArr);
        float f2 = fArr[0];
        float f3 = fArr[1];
        float f4 = fArr[2];
        float f5 = fArr[3];
        float f6 = fArr[4];
        float f7 = fArr[5];
        float f8 = fArr[6];
        float f9 = fArr[7];
        float f10 = fArr[8];
        fArr[0] = f2;
        fArr[1] = f5;
        fArr[2] = 0.0f;
        fArr[3] = f8;
        fArr[4] = f3;
        fArr[5] = f6;
        fArr[6] = 0.0f;
        fArr[7] = f9;
        fArr[8] = 0.0f;
        fArr[9] = 0.0f;
        fArr[10] = 1.0f;
        fArr[11] = 0.0f;
        fArr[12] = f4;
        fArr[13] = f7;
        fArr[14] = 0.0f;
        fArr[15] = f10;
    }

    public static final cd0 J(cd0 cd0Var, float f2) {
        return cd0Var.b(new nw0(f2, f2, f2, f2));
    }

    public static final cd0 K(cd0 cd0Var, float f2, float f3) {
        return cd0Var.b(new nw0(f2, f3, f2, f3));
    }

    public static ay0 L(int i2, Object obj) {
        float f2;
        if ((i2 & 2) != 0) {
            f2 = 1500.0f;
        } else {
            f2 = 400.0f;
        }
        if ((i2 & 4) != 0) {
            obj = null;
        }
        return new ay0(1.0f, f2, obj);
    }

    public static final ko0 M(j2 j2Var, hj hjVar, gy0 gy0Var, Float f2) {
        kk kkVar;
        q qVar;
        ed.b.getClass();
        dd ddVar = dd.a;
        c4 c4Var = new c4(22, j2Var, cr.e);
        ky0 c2 = o20.c(f2);
        yj yjVar = (yj) c4Var.g;
        os osVar = (os) c4Var.f;
        if (gy0Var.equals(hw0.a)) {
            kkVar = kk.e;
        } else {
            kkVar = kk.h;
        }
        bh bhVar = new bh(gy0Var, osVar, c2, f2, null, 2);
        yj I = f31.I(hjVar, yjVar);
        if (kkVar == kk.f) {
            qVar = new q70(I, bhVar);
        } else {
            qVar = new q(I, true);
        }
        qVar.o0(kkVar, qVar, bhVar);
        return new ko0(c2);
    }

    public static final Object N(yj yjVar) {
        Object n2 = yjVar.n(p, 0);
        n2.getClass();
        return n2;
    }

    public static final boolean O(Throwable th, vu vuVar) {
        List asList;
        Object invoke;
        th.getClass();
        Integer num = b30.a;
        bn bnVar = null;
        if (num != null && num.intValue() < 19) {
            Method method = im0.b;
            if (method != null && (invoke = method.invoke(th, null)) != null) {
                asList = Arrays.asList((Throwable[]) invoke);
                asList.getClass();
            } else {
                asList = er.e;
            }
        } else {
            Throwable[] suppressed = th.getSuppressed();
            suppressed.getClass();
            asList = Arrays.asList(suppressed);
            asList.getClass();
        }
        int size = asList.size();
        boolean z = false;
        for (int i2 = 0; i2 < size; i2++) {
            if (((Throwable) asList.get(i2)) instanceof bn) {
                return false;
            }
        }
        try {
            fh fhVar = (fh) vuVar.a();
            if (fhVar != null) {
                boolean z2 = fhVar.b;
                List list = fhVar.a;
                if (z2) {
                    int size2 = list.size();
                    for (int i3 = 0; i3 < size2; i3++) {
                        ((hh) list.get(i3)).getClass();
                    }
                } else if (!list.isEmpty()) {
                    z = true;
                }
            }
            if (z) {
                fhVar.getClass();
                bnVar = new bn(fhVar);
            }
        } catch (Throwable th2) {
            bnVar = th2;
        }
        if (bnVar != null) {
            o20.d(th, bnVar);
        }
        return z;
    }

    public static e31 P(int i2, eq eqVar, int i3) {
        if ((i3 & 4) != 0) {
            eqVar = gq.a;
        }
        return new e31(i2, eqVar);
    }

    public static final Object Q(yj yjVar, Object obj) {
        if (obj == null) {
            obj = N(yjVar);
        }
        if (obj == 0) {
            return o;
        }
        if (obj instanceof Integer) {
            return yjVar.n(r, new z11(((Number) obj).intValue(), yjVar));
        }
        Trace.beginSection(null);
        return x31.a;
    }

    public static final void a(final boolean z, final vu vuVar, bw bwVar, final int i2) {
        int i3;
        boolean z2;
        sf0 sf0Var;
        e3 e3Var;
        nh0 nh0Var;
        boolean z3;
        boolean z4;
        Object obj;
        Object obj2;
        int i4;
        int i5;
        bwVar.W(-361453782);
        int i6 = 2;
        if ((i2 & 6) == 0) {
            if (bwVar.g(z)) {
                i5 = 4;
            } else {
                i5 = 2;
            }
            i3 = i5 | i2;
        } else {
            i3 = i2;
        }
        if ((i2 & 48) == 0) {
            if (bwVar.h(vuVar)) {
                i4 = 32;
            } else {
                i4 = 16;
            }
            i3 |= i4;
        }
        int i7 = 0;
        if ((i3 & 19) != 18) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (bwVar.O(i3 & 1, z2)) {
            Object obj3 = (sf0) bwVar.j(pa0.a);
            mh0 mh0Var = null;
            if (obj3 == null) {
                bwVar.V(950836184);
                View view = (View) bwVar.j(p4.e);
                view.getClass();
                while (true) {
                    if (view != null) {
                        Object tag = view.getTag(R.id.view_tree_navigation_event_dispatcher_owner);
                        if (tag instanceof sf0) {
                            obj2 = (sf0) tag;
                        } else {
                            obj2 = null;
                        }
                        if (obj2 != null) {
                            obj3 = obj2;
                            break;
                        }
                        Object j2 = y20.j(view);
                        if (j2 instanceof View) {
                            view = (View) j2;
                        } else {
                            view = null;
                        }
                    } else {
                        obj3 = null;
                        break;
                    }
                }
                bwVar.p(false);
            } else {
                bwVar.V(950834231);
                bwVar.p(false);
            }
            if (obj3 == null) {
                bwVar.V(535274673);
                obj3 = (nh0) bwVar.j(qa0.a);
                if (obj3 == null) {
                    bwVar.V(1208426157);
                    View view2 = (View) bwVar.j(p4.e);
                    view2.getClass();
                    while (true) {
                        if (view2 != null) {
                            Object tag2 = view2.getTag(R.id.view_tree_on_back_pressed_dispatcher_owner);
                            if (tag2 instanceof nh0) {
                                obj = (nh0) tag2;
                            } else {
                                obj = null;
                            }
                            if (obj != null) {
                                obj3 = obj;
                                break;
                            }
                            Object j3 = y20.j(view2);
                            if (j3 instanceof View) {
                                view2 = (View) j3;
                            } else {
                                view2 = null;
                            }
                        } else {
                            obj3 = null;
                            break;
                        }
                    }
                } else {
                    bwVar.V(1208423708);
                }
                bwVar.p(false);
                if (obj3 == null) {
                    bwVar.V(1208428160);
                    Object obj4 = (Context) bwVar.j(p4.b);
                    while (true) {
                        if (obj4 instanceof ContextWrapper) {
                            if (obj4 instanceof nh0) {
                                break;
                            } else {
                                obj4 = ((ContextWrapper) obj4).getBaseContext();
                            }
                        } else {
                            obj4 = null;
                            break;
                        }
                    }
                    obj3 = (nh0) obj4;
                } else {
                    bwVar.V(1208423789);
                }
                bwVar.p(false);
            } else {
                bwVar.V(535271790);
            }
            bwVar.p(false);
            if (obj3 != null) {
                boolean f2 = bwVar.f(obj3);
                Object L = bwVar.L();
                Object obj5 = ph.a;
                if (f2 || L == obj5) {
                    if (obj3 instanceof sf0) {
                        sf0Var = (sf0) obj3;
                    } else {
                        sf0Var = null;
                    }
                    if (sf0Var != null) {
                        e3Var = ((lh0) ((mh0) ((cg) sf0Var).w.getValue()).b.getValue()).c;
                    } else {
                        e3Var = null;
                    }
                    if (obj3 instanceof nh0) {
                        nh0Var = (nh0) obj3;
                    } else {
                        nh0Var = null;
                    }
                    if (nh0Var != null) {
                        mh0Var = (mh0) ((cg) nh0Var).w.getValue();
                    }
                    L = new d9(e3Var, mh0Var);
                    bwVar.f0(L);
                }
                Object obj6 = (d9) L;
                long j4 = bwVar.T;
                boolean f3 = bwVar.f(obj6) | bwVar.e(j4);
                Object L2 = bwVar.L();
                Object obj7 = L2;
                if (f3 || L2 == obj5) {
                    vg vgVar = new vg(new e9(j4, obj3));
                    vgVar.c = new c2(3);
                    bwVar.f0(vgVar);
                    obj7 = vgVar;
                }
                final vg vgVar2 = (vg) obj7;
                bwVar.V(-585307852);
                boolean h2 = bwVar.h(vgVar2);
                if ((i3 & 112) == 32) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                boolean z5 = h2 | z3;
                Object L3 = bwVar.L();
                if (z5 || L3 == obj5) {
                    L3 = new f9(i7, vgVar2, vuVar);
                    bwVar.f0(L3);
                }
                dl.j((vu) L3, bwVar);
                int i8 = i3;
                Boolean valueOf = Boolean.valueOf(z);
                boolean h3 = bwVar.h(vgVar2);
                int i9 = i8 & 14;
                if (i9 == 4) {
                    z4 = true;
                } else {
                    z4 = false;
                }
                boolean z6 = h3 | z4;
                Object L4 = bwVar.L();
                if (z6 || L4 == obj5) {
                    L4 = new gv() { // from class: g9
                        @Override // defpackage.gv
                        public final Object e(Object obj8) {
                            vg vgVar3 = vg.this;
                            vgVar3.a(z);
                            return new j9((p80) obj8, vgVar3);
                        }
                    };
                    bwVar.f0(L4);
                }
                m20.b(valueOf, vgVar2, null, (gv) L4, bwVar, i9);
                boolean h4 = bwVar.h(obj6) | bwVar.h(vgVar2);
                Object L5 = bwVar.L();
                if (h4 || L5 == obj5) {
                    L5 = new c(i6, obj6, vgVar2);
                    bwVar.f0(L5);
                }
                dl.g(obj6, vgVar2, (gv) L5, bwVar);
                bwVar.p(false);
            } else {
                v7.o("No NavigationEventDispatcherOwner was provided via LocalNavigationEventDispatcherOwner and no OnBackPressedDispatcherOwner was provided via LocalOnBackPressedDispatcherOwner. Please provide one of the two.");
                return;
            }
        } else {
            bwVar.R();
        }
        mo0 r2 = bwVar.r();
        if (r2 != null) {
            r2.d = new kv() { // from class: h9
                @Override // defpackage.kv
                public final Object d(Object obj8, Object obj9) {
                    ((Integer) obj9).getClass();
                    int O = d20.O(i2 | 1);
                    k81.a(z, vuVar, (bw) obj8, O);
                    return x31.a;
                }
            };
        }
    }

    public static final void b(gg ggVar, bw bwVar, int i2) {
        boolean z;
        bwVar.W(-2127803308);
        int i3 = 2;
        if ((i2 & 3) != 2) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i2 & 1, z)) {
            ggVar.d(bwVar, 6);
        } else {
            bwVar.R();
        }
        mo0 r2 = bwVar.r();
        if (r2 != null) {
            r2.d = new o(i2, i3, ggVar);
        }
    }

    public static final long c(float f2, float f3) {
        return (Float.floatToRawIntBits(f3) & 4294967295L) | (Float.floatToRawIntBits(f2) << 32);
    }

    public static final void d(final uj0 uj0Var, final cd0 cd0Var, ba baVar, final dt0 dt0Var, float f2, bw bwVar, final int i2) {
        int i3;
        int i4;
        boolean z;
        final ba baVar2;
        final float f3;
        bwVar.W(1142754848);
        if (bwVar.h(uj0Var)) {
            i3 = 4;
        } else {
            i3 = 2;
        }
        int i5 = i2 | i3;
        if (bwVar.f(cd0Var)) {
            i4 = 256;
        } else {
            i4 = 128;
        }
        int i6 = i5 | i4 | 1772544;
        if ((599187 & i6) != 599186) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i6 & 1, z)) {
            ba baVar3 = x1.k;
            bwVar.V(1899381698);
            bwVar.p(false);
            cd0 G = n20.G(x(cd0Var.b(zc0.a), null, null, 520191), uj0Var, dt0Var, 1.0f, null, 2);
            Object L = bwVar.L();
            if (L == ph.a) {
                L = xa.e;
                bwVar.f0(L);
            }
            pc0 pc0Var = (pc0) L;
            long j2 = bwVar.T;
            int i7 = (int) (j2 ^ (j2 >>> 32));
            cd0 B = dl.B(bwVar, G);
            ll0 l2 = bwVar.l();
            jh.c.getClass();
            di diVar = ih.b;
            bwVar.Y();
            if (bwVar.S) {
                bwVar.k(diVar);
            } else {
                bwVar.i0();
            }
            m20.F(ih.e, bwVar, pc0Var);
            m20.F(ih.d, bwVar, l2);
            m20.C(ih.g, bwVar);
            m20.F(ih.c, bwVar, B);
            m20.F(ih.f, bwVar, Integer.valueOf(i7));
            bwVar.p(true);
            f3 = 1.0f;
            baVar2 = baVar3;
        } else {
            bwVar.R();
            baVar2 = baVar;
            f3 = f2;
        }
        mo0 r2 = bwVar.r();
        if (r2 != null) {
            r2.d = new kv(cd0Var, baVar2, dt0Var, f3, i2) { // from class: bz
                public final /* synthetic */ cd0 f;
                public final /* synthetic */ ba g;
                public final /* synthetic */ dt0 h;
                public final /* synthetic */ float i;

                @Override // defpackage.kv
                public final Object d(Object obj, Object obj2) {
                    ((Integer) obj2).getClass();
                    int O = d20.O(24633);
                    k81.d(uj0.this, this.f, this.g, this.h, this.i, (bw) obj, O);
                    return x31.a;
                }
            };
        }
    }

    public static float e(EdgeEffect edgeEffect, float f2, float f3, mm mmVar) {
        float f4;
        float f5 = hq.a;
        double B = mmVar.B() * 386.0878f * 160.0f * 0.84f;
        double d2 = hq.a * B;
        float exp = (float) (Math.exp((hq.b / hq.c) * Math.log((Math.abs(f2) * 0.35f) / d2)) * d2);
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 31) {
            f4 = p7.c(edgeEffect);
        } else {
            f4 = 0.0f;
        }
        if (exp > f4 * f3) {
            return 0.0f;
        }
        int G = jc0.G(f2);
        if (i2 >= 31) {
            edgeEffect.onAbsorb(G);
            return f2;
        }
        if (edgeEffect.isFinished()) {
            edgeEffect.onAbsorb(G);
        }
        return f2;
    }

    public static final void f(ef0 ef0Var, bd0 bd0Var) {
        ef0 w = E(bd0Var).w();
        int i2 = w.g - 1;
        Object[] objArr = w.e;
        if (i2 < objArr.length) {
            while (i2 >= 0) {
                ef0Var.b(((z40) objArr[i2]).H.f);
                i2--;
            }
        }
    }

    public static final float g(long j2, long j3) {
        return Math.min(Float.intBitsToFloat((int) (j3 >> 32)) / Float.intBitsToFloat((int) (j2 >> 32)), Float.intBitsToFloat((int) (j3 & 4294967295L)) / Float.intBitsToFloat((int) (j2 & 4294967295L)));
    }

    public static final bd0 h(ef0 ef0Var) {
        int i2;
        if (ef0Var != null && (i2 = ef0Var.g) != 0) {
            return (bd0) ef0Var.k(i2 - 1);
        }
        return null;
    }

    public static final wv i(wv wvVar) {
        if (wvVar == null) {
            wvVar = null;
        }
        if (wvVar != null) {
            return wvVar;
        }
        rh.b("Inconsistent composition");
        throw new RuntimeException();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final r40 j(bd0 bd0Var) {
        if ((bd0Var.g & 2) != 0) {
            if (bd0Var instanceof r40) {
                return (r40) bd0Var;
            }
            if (bd0Var instanceof jm) {
                bd0 bd0Var2 = ((jm) bd0Var).t;
                while (bd0Var2 != 0) {
                    if (bd0Var2 instanceof r40) {
                        return (r40) bd0Var2;
                    }
                    if ((bd0Var2 instanceof jm) && (bd0Var2.g & 2) != 0) {
                        bd0Var2 = ((jm) bd0Var2).t;
                    } else {
                        bd0Var2 = bd0Var2.j;
                    }
                }
            }
        }
        return null;
    }

    public static final cd0 k(cd0 cd0Var, long j2, zv0 zv0Var) {
        return cd0Var.b(new p9(j2, zv0Var));
    }

    public static final Bundle l(xj0... xj0VarArr) {
        Bundle bundle = new Bundle(xj0VarArr.length);
        for (xj0 xj0Var : xj0VarArr) {
            String str = (String) xj0Var.e;
            Object obj = xj0Var.f;
            if (obj == null) {
                bundle.putString(str, null);
            } else if (obj instanceof Boolean) {
                bundle.putBoolean(str, ((Boolean) obj).booleanValue());
            } else if (obj instanceof Byte) {
                bundle.putByte(str, ((Number) obj).byteValue());
            } else if (obj instanceof Character) {
                bundle.putChar(str, ((Character) obj).charValue());
            } else if (obj instanceof Double) {
                bundle.putDouble(str, ((Number) obj).doubleValue());
            } else if (obj instanceof Float) {
                bundle.putFloat(str, ((Number) obj).floatValue());
            } else if (obj instanceof Integer) {
                bundle.putInt(str, ((Number) obj).intValue());
            } else if (obj instanceof Long) {
                bundle.putLong(str, ((Number) obj).longValue());
            } else if (obj instanceof Short) {
                bundle.putShort(str, ((Number) obj).shortValue());
            } else if (obj instanceof Bundle) {
                bundle.putBundle(str, (Bundle) obj);
            } else if (obj instanceof CharSequence) {
                bundle.putCharSequence(str, (CharSequence) obj);
            } else if (obj instanceof Parcelable) {
                bundle.putParcelable(str, (Parcelable) obj);
            } else if (obj instanceof boolean[]) {
                bundle.putBooleanArray(str, (boolean[]) obj);
            } else if (obj instanceof byte[]) {
                bundle.putByteArray(str, (byte[]) obj);
            } else if (obj instanceof char[]) {
                bundle.putCharArray(str, (char[]) obj);
            } else if (obj instanceof double[]) {
                bundle.putDoubleArray(str, (double[]) obj);
            } else if (obj instanceof float[]) {
                bundle.putFloatArray(str, (float[]) obj);
            } else if (obj instanceof int[]) {
                bundle.putIntArray(str, (int[]) obj);
            } else if (obj instanceof long[]) {
                bundle.putLongArray(str, (long[]) obj);
            } else if (obj instanceof short[]) {
                bundle.putShortArray(str, (short[]) obj);
            } else if (obj instanceof Object[]) {
                Class<?> componentType = obj.getClass().getComponentType();
                componentType.getClass();
                if (Parcelable.class.isAssignableFrom(componentType)) {
                    bundle.putParcelableArray(str, (Parcelable[]) obj);
                } else if (String.class.isAssignableFrom(componentType)) {
                    bundle.putStringArray(str, (String[]) obj);
                } else if (CharSequence.class.isAssignableFrom(componentType)) {
                    bundle.putCharSequenceArray(str, (CharSequence[]) obj);
                } else if (Serializable.class.isAssignableFrom(componentType)) {
                    bundle.putSerializable(str, (Serializable) obj);
                } else {
                    throw new IllegalArgumentException("Illegal value array type " + componentType.getCanonicalName() + " for key \"" + str + '\"');
                }
            } else if (obj instanceof Serializable) {
                bundle.putSerializable(str, (Serializable) obj);
            } else if (obj instanceof IBinder) {
                bundle.putBinder(str, (IBinder) obj);
            } else if (obj instanceof Size) {
                bundle.putSize(str, (Size) obj);
            } else if (obj instanceof SizeF) {
                bundle.putSizeF(str, (SizeF) obj);
            } else {
                throw new IllegalArgumentException("Illegal value type " + obj.getClass().getCanonicalName() + " for key \"" + str + '\"');
            }
        }
        return bundle;
    }

    public static void m(int i2) {
        if (2 <= i2 && i2 < 37) {
            return;
        }
        throw new IllegalArgumentException("radix " + i2 + " was not in valid range " + new w10(2, 36, 1));
    }

    public static void n(int i2, int i3, int i4) {
        if (i2 >= 0 && i3 <= i4) {
            if (i2 <= i3) {
                return;
            }
            v7.m(d3.u("fromIndex: ", i2, " > toIndex: ", i3));
        } else {
            throw new IndexOutOfBoundsException("fromIndex: " + i2 + ", toIndex: " + i3 + ", size: " + i4);
        }
    }

    public static final Object o(os osVar, kv kvVar, sz0 sz0Var) {
        int i2 = vs.a;
        us usVar = new us(kvVar, null);
        cr crVar = cr.e;
        xb xbVar = xb.e;
        Object b2 = new ld(usVar, osVar, crVar, -2, xbVar).c(crVar, 0, xbVar).b(vg0.e, sz0Var);
        x31 x31Var = x31.a;
        ik ikVar = ik.e;
        if (b2 != ikVar) {
            b2 = x31Var;
        }
        if (b2 == ikVar) {
            return b2;
        }
        return x31Var;
    }

    public static final os p(os osVar) {
        if (osVar instanceof iy0) {
            return osVar;
        }
        if (osVar instanceof ao) {
            return osVar;
        }
        return new ao(osVar);
    }

    public static final boolean q(long j2, long j3) {
        if (j2 == j3) {
            return true;
        }
        return false;
    }

    public static final cd0 r(cd0 cd0Var, float f2) {
        zr zrVar;
        if (f2 == 1.0f) {
            zrVar = l;
        } else {
            zrVar = new zr(gn.f, f2);
        }
        return cd0Var.b(zrVar);
    }

    public static final Object s(ku0 ku0Var, long j2, kv kvVar) {
        while (true) {
            if (ku0Var.e >= j2 && !ku0Var.f()) {
                return ku0Var;
            }
            Object d2 = ku0Var.d();
            wq wqVar = a;
            if (d2 == wqVar) {
                return wqVar;
            }
            ku0 ku0Var2 = (ku0) ((ki) d2);
            if (ku0Var2 == null) {
                ku0Var2 = (ku0) kvVar.d(Long.valueOf(ku0Var.e + 1), ku0Var);
                if (ku0Var.i(ku0Var2)) {
                    if (ku0Var.f()) {
                        ku0Var.h();
                    }
                }
            }
            ku0Var = ku0Var2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0061 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /* JADX WARN: Type inference failed for: r6v2, types: [ep0, java.lang.Object] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final java.lang.Object t(defpackage.yk r5, defpackage.jj r6) {
        /*
            wq r0 = defpackage.o4.f
            boolean r1 = r6 instanceof defpackage.ys
            if (r1 == 0) goto L15
            r1 = r6
            ys r1 = (defpackage.ys) r1
            int r2 = r1.k
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L15
            int r2 = r2 - r3
            r1.k = r2
            goto L1a
        L15:
            ys r1 = new ys
            r1.<init>(r6)
        L1a:
            java.lang.Object r6 = r1.j
            int r2 = r1.k
            r3 = 1
            if (r2 == 0) goto L34
            if (r2 != r3) goto L2d
            ws r5 = r1.i
            ep0 r1 = r1.h
            defpackage.o30.x(r6)     // Catch: defpackage.a -> L2b
            goto L5d
        L2b:
            r6 = move-exception
            goto L59
        L2d:
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            defpackage.v7.o(r5)
            r5 = 0
            return r5
        L34:
            defpackage.o30.x(r6)
            ep0 r6 = new ep0
            r6.<init>()
            r6.e = r0
            ws r2 = new ws
            r4 = 0
            r2.<init>(r4, r6)
            r1.h = r6     // Catch: defpackage.a -> L55
            r1.i = r2     // Catch: defpackage.a -> L55
            r1.k = r3     // Catch: defpackage.a -> L55
            java.lang.Object r5 = r5.b(r2, r1)     // Catch: defpackage.a -> L55
            ik r1 = defpackage.ik.e
            if (r5 != r1) goto L53
            return r1
        L53:
            r1 = r6
            goto L5d
        L55:
            r5 = move-exception
            r1 = r6
            r6 = r5
            r5 = r2
        L59:
            java.lang.Object r2 = r6.e
            if (r2 != r5) goto L6a
        L5d:
            java.lang.Object r5 = r1.e
            if (r5 == r0) goto L62
            return r5
        L62:
            java.util.NoSuchElementException r5 = new java.util.NoSuchElementException
            java.lang.String r6 = "Expected at least one element"
            r5.<init>(r6)
            throw r5
        L6a:
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.k81.t(yk, jj):java.lang.Object");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:14:0x006b A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:16:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /* JADX WARN: Type inference failed for: r7v2, types: [ep0, java.lang.Object] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final java.lang.Object u(defpackage.os r5, defpackage.kv r6, defpackage.jj r7) {
        /*
            wq r0 = defpackage.o4.f
            boolean r1 = r7 instanceof defpackage.zs
            if (r1 == 0) goto L15
            r1 = r7
            zs r1 = (defpackage.zs) r1
            int r2 = r1.l
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r2 & r3
            if (r4 == 0) goto L15
            int r2 = r2 - r3
            r1.l = r2
            goto L1a
        L15:
            zs r1 = new zs
            r1.<init>(r7)
        L1a:
            java.lang.Object r7 = r1.k
            int r2 = r1.l
            r3 = 1
            if (r2 == 0) goto L38
            if (r2 != r3) goto L31
            zn r5 = r1.j
            ep0 r6 = r1.i
            sz0 r1 = r1.h
            kv r1 = (defpackage.kv) r1
            defpackage.o30.x(r7)     // Catch: defpackage.a -> L2f
            goto L67
        L2f:
            r7 = move-exception
            goto L63
        L31:
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            defpackage.v7.o(r5)
            r5 = 0
            return r5
        L38:
            defpackage.o30.x(r7)
            ep0 r7 = new ep0
            r7.<init>()
            r7.e = r0
            zn r2 = new zn
            r2.<init>(r6, r7)
            r4 = r6
            sz0 r4 = (defpackage.sz0) r4     // Catch: defpackage.a -> L5e
            r1.h = r4     // Catch: defpackage.a -> L5e
            r1.i = r7     // Catch: defpackage.a -> L5e
            r1.j = r2     // Catch: defpackage.a -> L5e
            r1.l = r3     // Catch: defpackage.a -> L5e
            java.lang.Object r5 = r5.b(r2, r1)     // Catch: defpackage.a -> L5e
            ik r1 = defpackage.ik.e
            if (r5 != r1) goto L5b
            return r1
        L5b:
            r1 = r6
            r6 = r7
            goto L67
        L5e:
            r5 = move-exception
            r1 = r6
            r6 = r7
            r7 = r5
            r5 = r2
        L63:
            java.lang.Object r2 = r7.e
            if (r2 != r5) goto L80
        L67:
            java.lang.Object r5 = r6.e
            if (r5 == r0) goto L6c
            return r5
        L6c:
            java.util.NoSuchElementException r5 = new java.util.NoSuchElementException
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "Expected at least one element matching the predicate "
            r6.<init>(r7)
            r6.append(r1)
            java.lang.String r6 = r6.toString()
            r5.<init>(r6)
            throw r5
        L80:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.k81.u(os, kv, jj):java.lang.Object");
    }

    public static boolean v() {
        Object obj;
        Method method;
        try {
            if (b4.P0 == null) {
                b4.P0 = Class.forName("android.os.SystemProperties");
            }
            Boolean bool = null;
            if (b4.Q0 == null) {
                Class cls = b4.P0;
                if (cls != null) {
                    method = cls.getDeclaredMethod("getBoolean", String.class, Boolean.TYPE);
                } else {
                    method = null;
                }
                b4.Q0 = method;
            }
            Method method2 = b4.Q0;
            if (method2 != null) {
                obj = method2.invoke(null, "debug.layout", Boolean.FALSE);
            } else {
                obj = null;
            }
            if (obj instanceof Boolean) {
                bool = (Boolean) obj;
            }
            return o20.e(bool, Boolean.TRUE);
        } catch (Exception unused) {
            return false;
        }
    }

    public static final cd0 w(cd0 cd0Var, gv gvVar) {
        return cd0Var.b(new ea(gvVar));
    }

    public static cd0 x(cd0 cd0Var, zv0 zv0Var, da daVar, int i2) {
        float f2;
        float f3;
        zv0 zv0Var2;
        boolean z;
        int i3;
        da daVar2;
        float f4 = 1.0f;
        if ((i2 & 1) != 0) {
            f2 = 1.0f;
        } else {
            f2 = 0.8f;
        }
        if ((i2 & 2) != 0) {
            f3 = 1.0f;
        } else {
            f3 = 0.8f;
        }
        if ((i2 & 4) == 0) {
            f4 = 0.0f;
        }
        float f5 = f4;
        long j2 = s21.a;
        if ((i2 & 2048) != 0) {
            zv0Var2 = o20.o;
        } else {
            zv0Var2 = zv0Var;
        }
        if ((i2 & 4096) != 0) {
            z = false;
        } else {
            z = true;
        }
        boolean z2 = z;
        long j3 = mx.a;
        if ((131072 & i2) != 0) {
            i3 = 3;
        } else {
            i3 = 12;
        }
        int i4 = i3;
        if ((i2 & 262144) != 0) {
            daVar2 = null;
        } else {
            daVar2 = daVar;
        }
        return cd0Var.b(new ix(f2, f3, f5, j2, zv0Var2, z2, j3, j3, i4, daVar2));
    }

    public static final void y(yj yjVar, Throwable th) {
        Throwable runtimeException;
        Iterator it = ck.a.iterator();
        while (it.hasNext()) {
            try {
                ((bk) it.next()).l(yjVar, th);
            } catch (Throwable th2) {
                if (th == th2) {
                    runtimeException = th;
                } else {
                    runtimeException = new RuntimeException("Exception while trying to handle coroutine exception", th2);
                    o20.d(runtimeException, th);
                }
                Thread currentThread = Thread.currentThread();
                currentThread.getUncaughtExceptionHandler().uncaughtException(currentThread, runtimeException);
            }
        }
        try {
            o20.d(th, new cn(yjVar));
        } catch (Throwable unused) {
        }
        Thread currentThread2 = Thread.currentThread();
        currentThread2.getUncaughtExceptionHandler().uncaughtException(currentThread2, th);
    }

    public static final cd0 z(cd0 cd0Var, float f2) {
        return cd0Var.b(new nw0(f2, f2, 5));
    }
}
