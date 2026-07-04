package defpackage;

import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class g6 extends bd0 implements ai, tp, j40 {
    public xq0 B;
    public yq0 C;
    public final je0 s;
    public final boolean t;
    public final float u;
    public final u2 v;
    public c9 w;
    public float x;
    public boolean z;
    public long y = 0;
    public final pe0 A = new pe0();

    public g6(je0 je0Var, boolean z, float f, u2 u2Var) {
        this.s = je0Var;
        this.t = z;
        this.u = f;
        this.v = u2Var;
    }

    @Override // defpackage.sc0
    public final void C(long j) {
        float G;
        this.z = true;
        mm mmVar = k81.E(this).A;
        this.y = d20.J(j);
        float f = this.u;
        if (Float.isNaN(f)) {
            long j2 = this.y;
            float intBitsToFloat = Float.intBitsToFloat((int) (j2 >> 32));
            float intBitsToFloat2 = Float.intBitsToFloat((int) (j2 & 4294967295L));
            G = ch0.d((Float.floatToRawIntBits(intBitsToFloat2) & 4294967295L) | (Float.floatToRawIntBits(intBitsToFloat) << 32)) / 2.0f;
            if (this.t) {
                G += mmVar.G(10.0f);
            }
        } else {
            G = mmVar.G(f);
        }
        this.x = G;
        pe0 pe0Var = this.A;
        Object[] objArr = pe0Var.a;
        int i = pe0Var.b;
        for (int i2 = 0; i2 < i; i2++) {
            D0((qn0) objArr[i2]);
        }
        pe0Var.d();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void D0(qn0 qn0Var) {
        yq0 yq0Var;
        Object remove;
        View view;
        xq0 xq0Var;
        if (qn0Var instanceof on0) {
            on0 on0Var = (on0) qn0Var;
            long j = this.y;
            float f = this.x;
            xq0 xq0Var2 = this.B;
            int i = 0;
            xq0 xq0Var3 = xq0Var2;
            if (xq0Var2 == null) {
                Object obj = (View) n20.p(this, p4.e);
                while (!(obj instanceof ViewGroup)) {
                    Object parent = ((View) obj).getParent();
                    if (parent instanceof View) {
                        obj = parent;
                    } else {
                        v7.i("Couldn't find a valid parent for ", obj, ". Are you overriding LocalView and providing a View that is not attached to the view hierarchy?");
                        return;
                    }
                }
                ViewGroup viewGroup = (ViewGroup) obj;
                int childCount = viewGroup.getChildCount();
                int i2 = 0;
                while (true) {
                    if (i2 < childCount) {
                        View childAt = viewGroup.getChildAt(i2);
                        if (childAt instanceof xq0) {
                            xq0Var = (xq0) childAt;
                            break;
                        }
                        i2++;
                    } else {
                        xq0 xq0Var4 = new xq0(viewGroup.getContext());
                        viewGroup.addView(xq0Var4);
                        xq0Var = xq0Var4;
                        break;
                    }
                }
                this.B = xq0Var;
                xq0Var3 = xq0Var;
            }
            ArrayList arrayList = xq0Var3.f;
            c4 c4Var = xq0Var3.h;
            LinkedHashMap linkedHashMap = (LinkedHashMap) c4Var.f;
            LinkedHashMap linkedHashMap2 = (LinkedHashMap) c4Var.f;
            LinkedHashMap linkedHashMap3 = (LinkedHashMap) c4Var.g;
            yq0 yq0Var2 = (yq0) linkedHashMap.get(this);
            View view2 = yq0Var2;
            if (yq0Var2 == null) {
                ArrayList arrayList2 = xq0Var3.g;
                arrayList2.getClass();
                if (arrayList2.isEmpty()) {
                    remove = null;
                } else {
                    remove = arrayList2.remove(0);
                }
                yq0 yq0Var3 = (yq0) remove;
                View view3 = yq0Var3;
                if (yq0Var3 == null) {
                    if (xq0Var3.i > jc0.q(arrayList)) {
                        View view4 = new View(xq0Var3.getContext());
                        xq0Var3.addView(view4);
                        arrayList.add(view4);
                        view = view4;
                    } else {
                        yq0 yq0Var4 = (yq0) arrayList.get(xq0Var3.i);
                        g6 g6Var = (g6) linkedHashMap3.get(yq0Var4);
                        view = yq0Var4;
                        if (g6Var != null) {
                            g6Var.C = null;
                            o20.t(g6Var);
                            yq0 yq0Var5 = (yq0) linkedHashMap2.get(g6Var);
                            if (yq0Var5 != null) {
                            }
                            linkedHashMap2.remove(g6Var);
                            yq0Var4.c();
                            view = yq0Var4;
                        }
                    }
                    int i3 = xq0Var3.i;
                    if (i3 < xq0Var3.e - 1) {
                        xq0Var3.i = i3 + 1;
                        view3 = view;
                    } else {
                        xq0Var3.i = 0;
                        view3 = view;
                    }
                }
                linkedHashMap2.put(this, view3);
                linkedHashMap3.put(view3, this);
                view2 = view3;
            }
            yq0 yq0Var6 = view2;
            yq0Var6.b(on0Var, this.t, j, jc0.G(f), this.v.a(), new f6(i, this));
            this.C = yq0Var6;
            o20.t(this);
            return;
        }
        if (qn0Var instanceof pn0) {
            yq0 yq0Var7 = this.C;
            if (yq0Var7 != null) {
                yq0Var7.d();
                return;
            }
            return;
        }
        if ((qn0Var instanceof nn0) && (yq0Var = this.C) != null) {
            yq0Var.d();
        }
    }

    @Override // defpackage.tp
    public final void R(b50 b50Var) {
        yc ycVar = b50Var.e;
        b50Var.r();
        c9 c9Var = this.w;
        if (c9Var != null) {
            float f = this.x;
            long a = this.v.a();
            float floatValue = ((Number) ((y6) c9Var.c).d()).floatValue();
            if (floatValue > 0.0f) {
                long b = se.b(a, floatValue);
                if (c9Var.b) {
                    float intBitsToFloat = Float.intBitsToFloat((int) (b50Var.j() >> 32));
                    float intBitsToFloat2 = Float.intBitsToFloat((int) (b50Var.j() & 4294967295L));
                    r7 r7Var = ycVar.f;
                    long v = r7Var.v();
                    r7Var.q().h();
                    try {
                        ((r7) ((j2) r7Var.f).f).q().n(0.0f, 0.0f, intBitsToFloat, intBitsToFloat2);
                        d3.n(b50Var, b, f);
                    } finally {
                        r7Var.q().f();
                        r7Var.G(v);
                    }
                } else {
                    d3.n(b50Var, b, f);
                }
            }
        }
        uc q = ycVar.f.q();
        yq0 yq0Var = this.C;
        if (yq0Var != null) {
            yq0Var.e(jc0.G(this.x), this.y, this.v.a());
            yq0Var.draw(j3.a(q));
        }
    }

    @Override // defpackage.bd0
    public final boolean q0() {
        return false;
    }

    @Override // defpackage.bd0
    public final void t0() {
        f31.G(p0(), null, new d(this, null, 17), 3);
    }

    @Override // defpackage.bd0
    public final void v0() {
        xq0 xq0Var = this.B;
        if (xq0Var != null) {
            this.C = null;
            o20.t(this);
            c4 c4Var = xq0Var.h;
            yq0 yq0Var = (yq0) ((LinkedHashMap) c4Var.f).get(this);
            if (yq0Var != null) {
                yq0Var.c();
                LinkedHashMap linkedHashMap = (LinkedHashMap) c4Var.f;
                yq0 yq0Var2 = (yq0) linkedHashMap.get(this);
                if (yq0Var2 != null) {
                }
                linkedHashMap.remove(this);
                xq0Var.g.add(yq0Var);
            }
        }
    }

    @Override // defpackage.tp
    public final /* synthetic */ void m0() {
    }

    @Override // defpackage.j40
    public final /* synthetic */ void x(l40 l40Var) {
    }
}
