package com.example.haojie_huang_stressmeter.ui.results

import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.haojie_huang_stressmeter.databinding.FragmentResultsBinding
import lecho.lib.hellocharts.gesture.ContainerScrollType
import lecho.lib.hellocharts.gesture.ZoomType
import lecho.lib.hellocharts.model.*
import java.io.File

class ResultsFragment : Fragment()
{

    private var _binding: FragmentResultsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var table: TableLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val chart = binding.stressChart

        chart.isInteractive = true
        chart.zoomType = ZoomType.HORIZONTAL_AND_VERTICAL
        chart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL)

        val data = LineChartData()
        val x_axis = Axis()
        val y_axis = Axis()
        x_axis.setName("Instances")
        y_axis.setName("Stress Level")
        data.axisXBottom = x_axis
        data.axisYLeft = y_axis

        val chart_values: ArrayList<PointValue> = ArrayList()
        table = binding.stressTable2
        val file = File(requireContext().getExternalFilesDir(null), "stress_timestamp.csv")
        if (file.exists())
        {
            var num = 0
            file.forEachLine {
                val strings = it.split(",").toTypedArray()
                val temp_time = it.split(",")[0]
                val stress_level = it.split(",")[1]
                addRow(table, temp_time, stress_level)
                chart_values.add(PointValue(num.toFloat(), stress_level.toFloat()))
                num++
            }
        }

        val chart_lines: ArrayList<Line> = ArrayList()
        val chart_line = Line(chart_values).setColor(Color.BLUE).setCubic(true)
        chart_line.isFilled = true
        chart_line.setHasLabels(false)
        chart_lines.add(chart_line)
        data.setLines(chart_lines)

        chart.lineChartData = data
        //some hellocharts code are reference from https://github.com/lecho/hellocharts-android

        val vp = Viewport(chart.maximumViewport)
        vp.set(0F, 16F, vp.right, 0F)
        chart.maximumViewport = vp
        chart.currentViewport = vp
        //reference from https://www.its203.com/article/u011168565/51982353

        return root
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }

    private fun addRow(table: TableLayout, timestamp: String, stress_level: String)
    {
        val metrics: DisplayMetrics = requireContext().getResources().getDisplayMetrics()
        val screenWidth = metrics.widthPixels
        val table_row = TableRow(context)
        val s1 = TextView(context)
        val s2 = TextView(context)
        val fake_line = TextView(context)
        s1.text = timestamp
        s2.text = stress_level
        fake_line.text = "|"
        s1.setPadding(15,0,screenWidth/5,0)
        s2.setPadding(0,0,0,0)
        fake_line.setPadding(10,0,10,0)
        table_row.addView(s1)
        table_row.addView(fake_line)
        table_row.addView(s2)
        val fake_line2 = View(context)
        fake_line2.layoutParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,5)
        fake_line2.setBackgroundColor(Color.parseColor("#000000"))
        table.addView(fake_line2)
        table.addView(table_row)
    }
}