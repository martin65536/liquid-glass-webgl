package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public abstract class h30 extends ab0 implements un, sz {
    public l30 k;

    @Override // defpackage.un
    public final void a() {
        q().e0(this);
    }

    @Override // defpackage.sz
    public final boolean b() {
        return true;
    }

    @Override // defpackage.sz
    public final pg0 d() {
        return null;
    }

    public d30 getParent() {
        return q();
    }

    public final l30 q() {
        l30 l30Var = this.k;
        if (l30Var != null) {
            return l30Var;
        }
        o20.G("job");
        throw null;
    }

    public abstract boolean r();

    public abstract void s(Throwable th);

    @Override // defpackage.ab0
    public final String toString() {
        return getClass().getSimpleName() + '@' + dl.v(this) + "[job@" + dl.v(q()) + ']';
    }
}
