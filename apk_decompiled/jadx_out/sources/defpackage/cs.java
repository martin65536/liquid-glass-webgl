package defpackage;

import java.util.Iterator;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class cs implements lv0 {
    public final /* synthetic */ int a;
    public final gv b;
    public final Object c;

    public /* synthetic */ cs(Object obj, gv gvVar, int i) {
        this.a = i;
        this.c = obj;
        this.b = gvVar;
    }

    @Override // defpackage.lv0
    public final Iterator iterator() {
        switch (this.a) {
            case 0:
                return new bs(this);
            default:
                return new iw(this);
        }
    }
}
