/*
 * MesControlFile.java
 *
 * Created on February 5, 2007, 5:36 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.multitier.midware.sys.datasource;

/**
 *
 * @author Administrator
 */
public class MesControlFile
{
	
	/** Creates a new instance of MesControlFile */
	public MesControlFile()
	{
	}

	private int id;

	private String object_type;

	private Long sequence_number;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getObject_type()
	{
		return object_type;
	}

	public void setObject_type(String object_type)
	{
		this.object_type = object_type;
	}

	public Long getSequence_number()
	{
		return sequence_number;
	}

	public void setSequence_number(Long sequence_number)
	{
		this.sequence_number = sequence_number;
	}
	
}
