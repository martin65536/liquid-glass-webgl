package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class kl0 extends ol0 {
    public ll0 j;

    public kl0(ll0 ll0Var) {
        this.e = new rt(12);
        this.f = ll0Var.e;
        this.i = ll0Var.f;
        this.j = ll0Var;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v3, types: [ml0] */
    public final ll0 b() {
        a31 a31Var = this.f;
        ll0 ll0Var = this.j;
        a31 a31Var2 = ll0Var.e;
        ll0 ll0Var2 = ll0Var;
        if (a31Var != a31Var2) {
            this.e = new rt(12);
            ll0Var2 = new ml0(this.f, this.i);
        }
        this.j = ll0Var2;
        return ll0Var2;
    }

    @Override // defpackage.ol0, java.util.AbstractMap, java.util.Map
    public final /* bridge */ boolean containsKey(Object obj) {
        if (!(obj instanceof do0)) {
            return false;
        }
        return super.containsKey((do0) obj);
    }

    @Override // java.util.AbstractMap, java.util.Map
    public final /* bridge */ boolean containsValue(Object obj) {
        if (!(obj instanceof i41)) {
            return false;
        }
        return super.containsValue((i41) obj);
    }

    @Override // defpackage.ol0, java.util.AbstractMap, java.util.Map
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

    @Override // defpackage.ol0, java.util.AbstractMap, java.util.Map
    public final /* bridge */ Object remove(Object obj) {
        if (!(obj instanceof do0)) {
            return null;
        }
        return (i41) super.remove((do0) obj);
    }
}
