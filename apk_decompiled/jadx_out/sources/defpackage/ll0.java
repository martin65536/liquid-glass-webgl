package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ll0 extends ml0 implements ci, zh {
    public static final ll0 h = new ml0(a31.e, 0);

    /* JADX WARN: Type inference failed for: r5v1, types: [ml0, ll0] */
    public final ll0 b(do0 do0Var, i41 i41Var) {
        pu u = this.e.u(do0Var.hashCode(), 0, do0Var, i41Var);
        if (u == null) {
            return this;
        }
        return new ml0((a31) u.b, this.f + u.a);
    }

    @Override // defpackage.ml0, java.util.Map
    public final /* bridge */ boolean containsKey(Object obj) {
        if (!(obj instanceof do0)) {
            return false;
        }
        return super.containsKey((do0) obj);
    }

    @Override // defpackage.ml0, java.util.Map
    public final /* bridge */ boolean containsValue(Object obj) {
        if (!(obj instanceof i41)) {
            return false;
        }
        return super.containsValue((i41) obj);
    }

    @Override // defpackage.ml0, java.util.Map
    public final /* bridge */ Object get(Object obj) {
        if (!(obj instanceof do0)) {
            return null;
        }
        return (i41) super.get((do0) obj);
    }

    @Override // java.util.Map
    public final /* bridge */ Object getOrDefault(Object obj, Object obj2) {
        if (!(obj instanceof do0)) {
            return obj2;
        }
        return (i41) super.getOrDefault((do0) obj, (i41) obj2);
    }
}
