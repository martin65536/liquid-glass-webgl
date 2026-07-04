package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class mi extends zb {
    public final xb t;

    public mi(int i, xb xbVar) {
        super(i);
        this.t = xbVar;
        if (xbVar != xb.e) {
            if (i >= 1) {
                return;
            }
            v7.h("Buffered channel capacity must be at least 1, but ", i, " was specified");
            throw null;
        }
        v7.i("This implementation does not support suspension for senders, use ", fp0.a(zb.class).b(), " instead");
        throw null;
    }

    public final Object M(Object obj, boolean z) {
        if (this.t == xb.g) {
            Object q = super.q(obj);
            if ((q instanceof nd) && !(q instanceof md)) {
                return x31.a;
            }
            return q;
        }
        return I(obj);
    }

    @Override // defpackage.zb, defpackage.jv0
    public final Object d(ij ijVar, Object obj) {
        if (!(M(obj, true) instanceof md)) {
            return x31.a;
        }
        throw s();
    }

    @Override // defpackage.zb, defpackage.jv0
    public final Object q(Object obj) {
        return M(obj, false);
    }

    @Override // defpackage.zb
    public final boolean z() {
        if (this.t == xb.f) {
            return true;
        }
        return false;
    }
}
