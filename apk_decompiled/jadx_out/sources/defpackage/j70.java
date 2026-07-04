package defpackage;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class j70 implements gv {
    public static final j70 f = new j70(0);
    public static final j70 g = new j70(1);
    public static final j70 h = new j70(2);
    public static final j70 i = new j70(3);
    public static final j70 j = new j70(4);
    public static final j70 k = new j70(5);
    public final /* synthetic */ int e;

    public /* synthetic */ j70(int i2) {
        this.e = i2;
    }

    @Override // defpackage.gv
    public final Object e(Object obj) {
        switch (this.e) {
            case 0:
                ((Number) obj).intValue();
                return null;
            case 1:
                if (o20.e(obj, Boolean.FALSE)) {
                    return new se(se.g);
                }
                obj.getClass();
                return new se(f31.e(((Integer) obj).intValue()));
            case 2:
                return Boolean.valueOf(obj instanceof jq);
            case 3:
                return Boolean.valueOf(obj instanceof jq);
            case 4:
                return Boolean.valueOf(obj instanceof jq);
            default:
                return Boolean.valueOf(obj instanceof jq);
        }
    }
}
