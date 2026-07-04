package defpackage;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import com.kyant.backdrop.catalog.R;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class nh {
    public final View a;
    public final th b;
    public final j80 c;
    public final ps0 d;
    public final w51 e;
    public final j2 f;
    public final j2 g;
    public final Configuration h;
    public final af0 i;
    public final c3 j;
    public final dt0 k;
    public final l3 l;
    public final k3 m;
    public final vt n;
    public final af0 o;
    public final ay p;
    public final r6 q;
    public final b50 r;
    public final t70 s;
    public final zc t;
    public int u;
    public final mh v;

    /* JADX WARN: Multi-variable type inference failed */
    public nh(nh nhVar, View view, th thVar, j80 j80Var, ps0 ps0Var, w51 w51Var) {
        Context context;
        j2 j2Var;
        Configuration configuration;
        af0 B;
        c3 c3Var;
        dt0 dt0Var;
        l3 l3Var;
        k3 k3Var;
        vt dt0Var2;
        af0 ik0Var;
        ay rtVar;
        r6 r6Var;
        zc zcVar;
        b50 b50Var;
        j2 j2Var2;
        View view2;
        if (nhVar != null && (view2 = nhVar.a) != null) {
            context = view2.getContext();
        } else {
            context = null;
        }
        boolean e = o20.e(context, view.getContext());
        this.a = view;
        this.b = thVar;
        this.c = j80Var;
        this.d = ps0Var;
        this.e = w51Var;
        if (e) {
            nhVar.getClass();
            j2Var = nhVar.f;
        } else {
            j2Var = new j2(9);
        }
        this.f = j2Var;
        this.g = (nhVar == null || (j2Var2 = nhVar.g) == null) ? new j2(20) : j2Var2;
        if (e) {
            nhVar.getClass();
            configuration = nhVar.h;
        } else {
            configuration = new Configuration(view.getContext().getResources().getConfiguration());
        }
        this.h = configuration;
        if (e) {
            nhVar.getClass();
            B = nhVar.i;
        } else {
            B = n30.B(new Configuration(configuration));
        }
        this.i = B;
        if (e) {
            nhVar.getClass();
            c3Var = nhVar.j;
        } else {
            Context context2 = view.getContext();
            Object obj = new Object();
            Object systemService = context2.getSystemService("accessibility");
            systemService.getClass();
            c3Var = obj;
        }
        this.j = c3Var;
        if (e) {
            nhVar.getClass();
            dt0Var = nhVar.k;
        } else {
            view.getContext();
            dt0Var = new dt0(12);
        }
        this.k = dt0Var;
        if (e) {
            nhVar.getClass();
            l3Var = nhVar.l;
        } else {
            l3Var = new l3(view.getContext());
        }
        this.l = l3Var;
        if (e) {
            nhVar.getClass();
            k3Var = nhVar.m;
        } else {
            k3Var = new Object();
        }
        this.m = k3Var;
        if (e) {
            nhVar.getClass();
            dt0Var2 = nhVar.n;
        } else {
            view.getContext();
            dt0Var2 = new dt0(9);
        }
        this.n = dt0Var2;
        if (e) {
            nhVar.getClass();
            ik0Var = nhVar.o;
        } else {
            ik0Var = new ik0(o20.l(view.getContext()), x1.V);
        }
        this.o = ik0Var;
        if (view == (nhVar != null ? nhVar.a : null)) {
            rtVar = nhVar.p;
        } else {
            rtVar = new rt(17);
        }
        this.p = rtVar;
        if (e) {
            nhVar.getClass();
            r6Var = nhVar.q;
        } else {
            r6Var = new r6(ViewConfiguration.get(view.getContext()));
        }
        this.q = r6Var;
        this.r = (nhVar == null || (b50Var = nhVar.r) == null) ? new b50() : b50Var;
        this.s = new t70(0);
        this.t = (nhVar == null || (zcVar = nhVar.t) == null) ? new zc() : zcVar;
        new n9(1, this);
        this.v = new mh(this);
    }

    public final void a(b4 b4Var, kv kvVar, bw bwVar, int i) {
        int i2;
        int i3;
        int i4;
        boolean z;
        Set set;
        char c;
        char c2;
        String str;
        boolean z2;
        View view;
        Object obj;
        bwVar.W(123858079);
        if (bwVar.h(b4Var)) {
            i2 = 4;
        } else {
            i2 = 2;
        }
        int i5 = i2 | i;
        if (bwVar.h(kvVar)) {
            i3 = 32;
        } else {
            i3 = 16;
        }
        int i6 = i5 | i3;
        if (bwVar.h(this)) {
            i4 = 256;
        } else {
            i4 = 128;
        }
        int i7 = i6 | i4;
        int i8 = 1;
        if ((i7 & 147) != 146) {
            z = true;
        } else {
            z = false;
        }
        if (bwVar.O(i7 & 1, z)) {
            Object tag = b4Var.getTag(R.id.inspection_slot_table_set);
            LinkedHashMap linkedHashMap = null;
            if ((tag instanceof Set) && (!(tag instanceof q30) || (tag instanceof s30))) {
                set = (Set) tag;
            } else {
                set = null;
            }
            if (set == null) {
                Object parent = b4Var.getParent();
                if (parent instanceof View) {
                    view = (View) parent;
                } else {
                    view = null;
                }
                if (view != null) {
                    obj = view.getTag(R.id.inspection_slot_table_set);
                } else {
                    obj = null;
                }
                if ((obj instanceof Set) && (!(obj instanceof q30) || (obj instanceof s30))) {
                    set = (Set) obj;
                } else {
                    set = null;
                }
            }
            if (set != null) {
                set.add(bwVar.w());
                bwVar.q = true;
                bwVar.C = true;
                bwVar.c.b();
                bwVar.H.b();
                uw0 uw0Var = bwVar.I;
                rw0 rw0Var = uw0Var.a;
                uw0Var.e = rw0Var.n;
                uw0Var.f = rw0Var.o;
            }
            Object L = bwVar.L();
            ps0 ps0Var = this.d;
            dt0 dt0Var = ph.a;
            if (L == dt0Var) {
                Object parent2 = b4Var.getParent();
                parent2.getClass();
                View view2 = (View) parent2;
                Object tag2 = view2.getTag(R.id.compose_view_saveable_id_tag);
                if (tag2 instanceof String) {
                    str = (String) tag2;
                } else {
                    str = null;
                }
                if (str == null) {
                    str = String.valueOf(view2.getId());
                }
                String str2 = "SaveableStateRegistry:" + str;
                c4 b = ps0Var.b();
                Bundle m = b.m(str2);
                if (m != null) {
                    linkedHashMap = new LinkedHashMap();
                    for (String str3 : m.keySet()) {
                        ArrayList parcelableArrayList = m.getParcelableArrayList(str3);
                        parcelableArrayList.getClass();
                        linkedHashMap.put(str3, parcelableArrayList);
                    }
                }
                c = 4;
                c2 = 2;
                w3 w3Var = w3.p;
                qy0 qy0Var = gs0.a;
                fs0 fs0Var = new fs0(linkedHashMap, w3Var);
                if (b.q(str2) == null) {
                    try {
                        b.u(str2, new vf(i8, fs0Var));
                        z2 = true;
                    } catch (IllegalArgumentException unused) {
                    }
                    vn vnVar = new vn(fs0Var, new wn(z2, b, str2));
                    bwVar.f0(vnVar);
                    L = vnVar;
                }
                z2 = false;
                vn vnVar2 = new vn(fs0Var, new wn(z2, b, str2));
                bwVar.f0(vnVar2);
                L = vnVar2;
            } else {
                c = 4;
                c2 = 2;
            }
            vn vnVar3 = (vn) L;
            boolean h = bwVar.h(vnVar3);
            Object L2 = bwVar.L();
            if (h || L2 == dt0Var) {
                L2 = new q2(10, vnVar3);
                bwVar.f0(L2);
            }
            dl.f(x31.a, (gv) L2, bwVar);
            gi giVar = fi.w;
            boolean booleanValue = ((Boolean) bwVar.j(giVar)).booleanValue() | b4Var.getScrollCaptureInProgress$ui();
            boolean f = bwVar.f(b4Var.getView());
            Object L3 = bwVar.L();
            if (f || L3 == dt0Var) {
                b4Var.getView();
                L3 = new Object();
                bwVar.f0(L3);
            }
            eo0 a = oa0.a.a(this.c);
            eo0 a2 = sa0.a.a(ps0Var);
            eo0 a3 = p4.c.a(this.f);
            eo0 a4 = p4.d.a(this.g);
            eo0 a5 = p4.b.a(b4Var.getContext());
            eo0 a6 = o10.a.a(set);
            eo0 a7 = p4.a.a(b4Var.getConfiguration());
            eo0 a8 = gs0.a.a(vnVar3);
            eo0 a9 = p4.e.a(b4Var.getView());
            eo0 a10 = giVar.a(Boolean.valueOf(booleanValue));
            eo0 a11 = fi.t.a(b4Var.getViewConfiguration());
            eo0 a12 = vy.a.a((x51) L3);
            eo0[] eo0VarArr = new eo0[12];
            eo0VarArr[0] = a;
            eo0VarArr[1] = a2;
            eo0VarArr[c2] = a3;
            eo0VarArr[3] = a4;
            eo0VarArr[c] = a5;
            eo0VarArr[5] = a6;
            eo0VarArr[6] = a7;
            eo0VarArr[7] = a8;
            eo0VarArr[8] = a9;
            eo0VarArr[9] = a10;
            eo0VarArr[10] = a11;
            eo0VarArr[11] = a12;
            o20.b(eo0VarArr, jc0.C(1317454175, new lh(b4Var, this, kvVar), bwVar), bwVar, 56);
        } else {
            bwVar.R();
        }
        mo0 r = bwVar.r();
        if (r != null) {
            r.d = new lh(this, b4Var, kvVar, i);
        }
    }

    public final void b() {
        int i = this.u - 1;
        this.u = i;
        if (i < 0) {
            Log.e("ComposeViewContext", "View count has dropped below 0");
            this.u = 0;
        }
        if (this.u == 0) {
            View view = this.a;
            Context context = view.getContext();
            mh mhVar = this.v;
            context.unregisterComponentCallbacks(mhVar);
            this.s.getClass();
            view.getViewTreeObserver().removeOnWindowFocusChangeListener(mhVar);
        }
    }

    public final void c() {
        int i = this.u + 1;
        this.u = i;
        if (i == 1) {
            View view = this.a;
            Context context = view.getContext();
            mh mhVar = this.v;
            context.registerComponentCallbacks(mhVar);
            d(view.getResources().getConfiguration());
            this.s.a.setValue(Boolean.valueOf(view.hasWindowFocus()));
            view.getViewTreeObserver().addOnWindowFocusChangeListener(mhVar);
        }
    }

    public final void d(Configuration configuration) {
        int updateFrom = this.h.updateFrom(configuration);
        if (updateFrom != 0) {
            Iterator it = ((HashMap) this.f.f).entrySet().iterator();
            while (it.hasNext()) {
                if (((WeakReference) ((Map.Entry) it.next()).getValue()).get() == null) {
                    it.remove();
                } else {
                    v7.d();
                    return;
                }
            }
            this.i.setValue(new Configuration(configuration));
            j2 j2Var = this.g;
            synchronized (j2Var) {
                ((he0) j2Var.f).c();
            }
            if ((268435456 & updateFrom) != 0) {
                this.o.setValue(o20.l(this.a.getContext()));
            }
            if (((-1342235264) & updateFrom) != 0) {
                this.s.getClass();
            }
        }
    }
}
