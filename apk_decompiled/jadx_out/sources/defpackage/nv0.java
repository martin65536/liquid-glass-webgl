package defpackage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public abstract class nv0 extends pv0 {
    public static List G(lv0 lv0Var) {
        Iterator it = lv0Var.iterator();
        if (!it.hasNext()) {
            return er.e;
        }
        Object next = it.next();
        if (!it.hasNext()) {
            return jc0.v(next);
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(next);
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        return arrayList;
    }
}
