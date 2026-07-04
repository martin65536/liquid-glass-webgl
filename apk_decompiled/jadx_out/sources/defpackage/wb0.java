package defpackage;

import java.util.LinkedHashMap;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class wb0 implements sr0 {
    public final LinkedHashMap e;

    public wb0(int i) {
        switch (i) {
            case 1:
                this.e = new LinkedHashMap();
                return;
            case 2:
                this.e = new LinkedHashMap();
                return;
            default:
                this.e = new LinkedHashMap(0, 0.75f, true);
                return;
        }
    }

    @Override // defpackage.sr0
    public h6 r(String str, String str2) {
        LinkedHashMap linkedHashMap = this.e;
        Object obj = linkedHashMap.get(str);
        if (obj == null) {
            obj = g1.a(str2);
            linkedHashMap.put(str, obj);
        }
        return (h6) obj;
    }
}
