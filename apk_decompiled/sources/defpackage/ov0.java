package defpackage;

import java.util.Iterator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class ov0 implements lv0 {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ ov0(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    @Override // defpackage.lv0
    public final Iterator iterator() {
        int i = this.a;
        Object obj = this.b;
        switch (i) {
            case 0:
                return g30.y((kv) obj);
            default:
                return (Iterator) obj;
        }
    }
}
