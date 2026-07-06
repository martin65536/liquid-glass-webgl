package defpackage;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final class uo0 implements ns0 {
    public final LinkedHashSet a = new LinkedHashSet();

    public uo0(c4 c4Var) {
        c4Var.u("androidx.savedstate.Restarter", this);
    }

    @Override // defpackage.ns0
    public final Bundle a() {
        ArrayList<String> arrayList;
        Bundle l = k81.l((xj0[]) Arrays.copyOf(new xj0[0], 0));
        List d0 = me.d0(this.a);
        if (d0 instanceof ArrayList) {
            arrayList = (ArrayList) d0;
        } else {
            arrayList = new ArrayList<>(d0);
        }
        l.putStringArrayList("classes_to_restore", arrayList);
        return l;
    }
}
