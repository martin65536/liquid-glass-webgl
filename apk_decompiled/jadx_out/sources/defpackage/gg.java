package defpackage;

import java.util.ArrayList;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class gg implements kv, lv, mv, nv, ov, pv, qv, rv, wu, xu, zu, av, bv, cv, dv, ev, fv, hv, iv {
    public final int e;
    public final boolean f;
    public Object g;
    public mo0 h;
    public ArrayList i;

    public gg(int i, boolean z, Object obj) {
        this.e = i;
        this.f = z;
        this.g = obj;
    }

    @Override // defpackage.lv
    public final /* bridge */ /* synthetic */ Object c(Object obj, Object obj2, Object obj3) {
        return g(obj, (bw) obj2, ((Number) obj3).intValue());
    }

    @Override // defpackage.kv
    public final /* bridge */ /* synthetic */ Object d(Object obj, Object obj2) {
        return f((bw) obj, ((Number) obj2).intValue());
    }

    public final Object f(bw bwVar, int i) {
        int i2;
        bwVar.W(this.e);
        j(bwVar);
        if (bwVar.f(this)) {
            i2 = jc0.i(2, 0);
        } else {
            i2 = jc0.i(1, 0);
        }
        int i3 = i | i2;
        Object obj = this.g;
        obj.getClass();
        f31.n(2, obj);
        Object d = ((kv) obj).d(bwVar, Integer.valueOf(i3));
        mo0 r = bwVar.r();
        if (r != null) {
            r.d = new fg(2, this, gg.class, "invoke", "invoke(Landroidx/compose/runtime/Composer;I)Ljava/lang/Object;", 8, 0);
        }
        return d;
    }

    public final Object g(Object obj, bw bwVar, int i) {
        int i2;
        bwVar.W(this.e);
        j(bwVar);
        if (bwVar.f(this)) {
            i2 = jc0.i(2, 1);
        } else {
            i2 = jc0.i(1, 1);
        }
        Object obj2 = this.g;
        obj2.getClass();
        f31.n(3, obj2);
        Object c = ((lv) obj2).c(obj, bwVar, Integer.valueOf(i2 | i));
        mo0 r = bwVar.r();
        if (r != null) {
            r.d = new eg(i, 0, this, obj);
        }
        return c;
    }

    @Override // defpackage.mv
    public final /* bridge */ /* synthetic */ Object h(Object obj, Object obj2, Object obj3, Object obj4) {
        return i(obj, obj2, (bw) obj3, ((Number) obj4).intValue());
    }

    public final Object i(Object obj, Object obj2, bw bwVar, int i) {
        int i2;
        bwVar.W(this.e);
        j(bwVar);
        if (bwVar.f(this)) {
            i2 = jc0.i(2, 2);
        } else {
            i2 = jc0.i(1, 2);
        }
        Object obj3 = this.g;
        obj3.getClass();
        f31.n(4, obj3);
        Object h = ((mv) obj3).h(obj, obj2, bwVar, Integer.valueOf(i2 | i));
        mo0 r = bwVar.r();
        if (r != null) {
            r.d = new fb(this, obj, obj2, i);
        }
        return h;
    }

    public final void j(bw bwVar) {
        mo0 x;
        if (this.f && (x = bwVar.x()) != null) {
            x.b |= 1;
            if (jc0.F(this.h, x)) {
                this.h = x;
                return;
            }
            ArrayList arrayList = this.i;
            if (arrayList == null) {
                ArrayList arrayList2 = new ArrayList();
                this.i = arrayList2;
                arrayList2.add(x);
                return;
            }
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                if (jc0.F((mo0) arrayList.get(i), x)) {
                    arrayList.set(i, x);
                    return;
                }
            }
            arrayList.add(x);
        }
    }
}
