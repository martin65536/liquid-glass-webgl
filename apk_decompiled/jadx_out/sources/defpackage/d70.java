package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class d70 extends co0 implements t30, vu {
    public final /* synthetic */ int l;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ d70(int i, int i2, Class cls, Object obj, String str, String str2) {
        super(obj, cls, str, str2, i);
        this.l = i2;
    }

    @Override // defpackage.vu
    public final Object a() {
        int i = this.l;
        Object obj = this.f;
        switch (i) {
            case 0:
                return ((hy0) obj).getValue();
            default:
                return obj.getClass().getSimpleName();
        }
    }

    @Override // defpackage.jc
    public final p30 f() {
        fp0.a.getClass();
        return this;
    }
}
