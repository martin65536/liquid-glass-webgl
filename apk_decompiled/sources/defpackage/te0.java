package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class te0 extends co0 implements t30, gv {
    public te0(String str, String str2) {
        super(ic.e, zu0.class, str, str2, 1);
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        j();
        throw null;
    }

    @Override // defpackage.jc
    public final p30 f() {
        fp0.a.getClass();
        return this;
    }

    public final void j() {
        if (!this.k) {
            p30 i = i();
            if (i != this) {
                ((te0) ((t30) i)).j();
                return;
            }
            throw new Error("Kotlin reflection implementation is not found at runtime. Make sure you have kotlin-reflect.jar in the classpath");
        }
        throw new UnsupportedOperationException("Kotlin reflection is not yet supported for synthetic Java properties. Please follow/upvote https://youtrack.jetbrains.com/issue/KT-55980");
    }
}
