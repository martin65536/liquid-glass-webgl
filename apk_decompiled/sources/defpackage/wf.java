package defpackage;

import android.content.Context;
import android.os.Bundle;
import com.kyant.backdrop.catalog.MainActivity;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/* compiled from: r8-map-id-3b96d3c35d690309f46512ed3b40f5bf8cb92090229117673df7fcbbe5498310 */
/* loaded from: classes.dex */
public final /* synthetic */ class wf {
    public final /* synthetic */ MainActivity a;

    public /* synthetic */ wf(MainActivity mainActivity) {
        this.a = mainActivity;
    }

    public final void a(Context context) {
        context.getClass();
        MainActivity mainActivity = this.a;
        Bundle m = ((c4) mainActivity.h.g).m("android:support:activity-result");
        if (m != null) {
            ag agVar = mainActivity.l;
            LinkedHashMap linkedHashMap = agVar.b;
            LinkedHashMap linkedHashMap2 = agVar.a;
            Bundle bundle = agVar.g;
            ArrayList<Integer> integerArrayList = m.getIntegerArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_RCS");
            ArrayList<String> stringArrayList = m.getStringArrayList("KEY_COMPONENT_ACTIVITY_REGISTERED_KEYS");
            if (stringArrayList != null && integerArrayList != null) {
                ArrayList<String> stringArrayList2 = m.getStringArrayList("KEY_COMPONENT_ACTIVITY_LAUNCHED_KEYS");
                if (stringArrayList2 != null) {
                    agVar.d.addAll(stringArrayList2);
                }
                Bundle bundle2 = m.getBundle("KEY_COMPONENT_ACTIVITY_PENDING_RESULT");
                if (bundle2 != null) {
                    bundle.putAll(bundle2);
                }
                int size = stringArrayList.size();
                for (int i = 0; i < size; i++) {
                    String str = stringArrayList.get(i);
                    if (linkedHashMap.containsKey(str)) {
                        Integer num = (Integer) linkedHashMap.remove(str);
                        if (!bundle.containsKey(str)) {
                            f31.k(linkedHashMap2).remove(num);
                        }
                    }
                    Integer num2 = integerArrayList.get(i);
                    num2.getClass();
                    int intValue = num2.intValue();
                    String str2 = stringArrayList.get(i);
                    str2.getClass();
                    String str3 = str2;
                    linkedHashMap2.put(Integer.valueOf(intValue), str3);
                    agVar.b.put(str3, Integer.valueOf(intValue));
                }
            }
        }
    }
}
