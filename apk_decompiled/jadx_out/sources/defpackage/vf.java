package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import com.kyant.backdrop.catalog.MainActivity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class vf implements ns0 {
    public final /* synthetic */ int a;
    public final /* synthetic */ Object b;

    public /* synthetic */ vf(int i, Object obj) {
        this.a = i;
        this.b = obj;
    }

    @Override // defpackage.ns0
    public final Bundle a() {
        ArrayList<? extends Parcelable> arrayList;
        Map map;
        xj0[] xj0VarArr;
        int i = this.a;
        Object obj = this.b;
        switch (i) {
            case 0:
                Bundle bundle = new Bundle();
                ag agVar = ((MainActivity) obj).l;
                agVar.getClass();
                LinkedHashMap linkedHashMap = agVar.b;
                bundle.putIntegerArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_RCS", new ArrayList<>(linkedHashMap.values()));
                bundle.putStringArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_KEYS", new ArrayList<>(linkedHashMap.keySet()));
                bundle.putStringArrayList("KEY_COMPONENT_ACTIVITY_LAUNCHED_KEYS", new ArrayList<>(agVar.d));
                bundle.putBundle("KEY_COMPONENT_ACTIVITY_PENDING_RESULT", new Bundle(agVar.g));
                return bundle;
            case 1:
                Map d = ((fs0) obj).d();
                Bundle bundle2 = new Bundle();
                for (Map.Entry entry : d.entrySet()) {
                    String str = (String) entry.getKey();
                    List list = (List) entry.getValue();
                    if (list instanceof ArrayList) {
                        arrayList = (ArrayList) list;
                    } else {
                        arrayList = new ArrayList<>(list);
                    }
                    bundle2.putParcelableArrayList(str, arrayList);
                }
                return bundle2;
            default:
                a9 a9Var = (a9) obj;
                LinkedHashMap linkedHashMap2 = (LinkedHashMap) a9Var.d;
                linkedHashMap2.getClass();
                int size = linkedHashMap2.size();
                Map map2 = fr.e;
                if (size != 0) {
                    if (size != 1) {
                        map = new LinkedHashMap(linkedHashMap2);
                    } else {
                        Map.Entry entry2 = (Map.Entry) linkedHashMap2.entrySet().iterator().next();
                        map = Collections.singletonMap(entry2.getKey(), entry2.getValue());
                        map.getClass();
                    }
                } else {
                    map = map2;
                }
                for (Map.Entry entry3 : map.entrySet()) {
                    a9Var.g(((ky0) entry3.getValue()).getValue(), (String) entry3.getKey());
                }
                LinkedHashMap linkedHashMap3 = (LinkedHashMap) a9Var.b;
                linkedHashMap3.getClass();
                int size2 = linkedHashMap3.size();
                if (size2 != 0) {
                    if (size2 != 1) {
                        map2 = new LinkedHashMap(linkedHashMap3);
                    } else {
                        Map.Entry entry4 = (Map.Entry) linkedHashMap3.entrySet().iterator().next();
                        map2 = Collections.singletonMap(entry4.getKey(), entry4.getValue());
                        map2.getClass();
                    }
                }
                for (Map.Entry entry5 : map2.entrySet()) {
                    a9Var.g(((ns0) entry5.getValue()).a(), (String) entry5.getKey());
                }
                LinkedHashMap linkedHashMap4 = (LinkedHashMap) a9Var.a;
                if (linkedHashMap4.isEmpty()) {
                    xj0VarArr = new xj0[0];
                } else {
                    ArrayList arrayList2 = new ArrayList(linkedHashMap4.size());
                    for (Map.Entry entry6 : linkedHashMap4.entrySet()) {
                        arrayList2.add(new xj0((String) entry6.getKey(), entry6.getValue()));
                    }
                    xj0VarArr = (xj0[]) arrayList2.toArray(new xj0[0]);
                }
                return k81.l((xj0[]) Arrays.copyOf(xj0VarArr, xj0VarArr.length));
        }
    }
}
